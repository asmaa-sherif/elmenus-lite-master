package spring.practice.elmenus_lite.service.restaurant;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantRegisterRequestDto;
import spring.practice.elmenus_lite.dto.restaurant.RestaurantResponseDto;
import spring.practice.elmenus_lite.entity.restaurant.Category;
import spring.practice.elmenus_lite.entity.restaurant.Restaurant;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantCategory;
import spring.practice.elmenus_lite.entity.restaurant.RestaurantDetails;
import spring.practice.elmenus_lite.handlerException.BusinessException;
import spring.practice.elmenus_lite.mapper.RestaurantMapper;
import spring.practice.elmenus_lite.repository.restaurant.CategoryRepository;
import spring.practice.elmenus_lite.repository.restaurant.RestaurantCategoryRepository;
import spring.practice.elmenus_lite.repository.restaurant.RestaurantDetailsRepository;
import spring.practice.elmenus_lite.repository.restaurant.RestaurantRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantDetailsRepository restaurantDetailsRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final RestaurantMapper restaurantMapper;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    private static final Pattern DELIVERY_TIME_PATTERN = Pattern.compile("^(\\d{1,3})\\s*(min|minutes|hour|hours)$", Pattern.CASE_INSENSITIVE);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Transactional
    @Override
    public RestaurantResponseDto registerRestaurant(RestaurantRegisterRequestDto request) {
        log.info("Registering restaurant: {}", request.getRestaurantName());

        validateRestaurantRequest(request);

        List<Category> categories = validateAndGetCategories(request.getCategoryIds());

        Restaurant restaurant = createAndSaveRestaurant(request);
        RestaurantDetails details = createAndSaveRestaurantDetails(restaurant, request);
        addCategoriesToRestaurant(restaurant, categories);

        restaurant.setRestaurantDetails(details);

        log.info("Restaurant registered successfully with ID: {}", restaurant.getRestaurantId());
        return restaurantMapper.toDto(restaurant);
    }

    @Transactional
    @Override
    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRegisterRequestDto request) {
        Restaurant restaurant = getRestaurant(id);

        validateRestaurantNameUniquenessForUpdate(request.getRestaurantName(), id);
        validateBusinessHours(request.getOpenTime(), request.getCloseTime());
        validateDeliveryTimeFormat(request.getEstimatedDeliveryTime());
        List<Category> categories = validateAndGetCategories(request.getCategoryIds());

        restaurant.setRestaurantName(request.getRestaurantName());
        updateNewRestaurantDetails(request, restaurant);
        updateRestaurantCategories(restaurant, categories);

        return restaurantMapper.toDto(restaurant);
    }

    @Transactional
    @Override
    public void setActiveStatus(Long id, boolean isActive) {
        Restaurant restaurant = getRestaurant(id);
        restaurant.setActive(isActive);
    }

    private void updateRestaurantCategories(Restaurant restaurant, List<Category> categories) {
        restaurant.getRestaurantCategories().clear();
        restaurantCategoryRepository.deleteByRestaurant(restaurant);
        addCategoriesToRestaurant(restaurant, categories);

    }

    private void updateNewRestaurantDetails(RestaurantRegisterRequestDto request, Restaurant restaurant) {
        RestaurantDetails details = restaurant.getRestaurantDetails();
        if (details == null) {
            details = new RestaurantDetails();
            details.setRestaurant(restaurant);
        }

        RestaurantDetails updatedDetails = buildRestaurantDetails(restaurant, request);
        details.setDescription(updatedDetails.getDescription());
        details.setPhone(updatedDetails.getPhone());
        details.setEstimatedDeliveryTime(updatedDetails.getEstimatedDeliveryTime());
        details.setOpenTime(updatedDetails.getOpenTime());
        details.setCloseTime(updatedDetails.getCloseTime());
        details.setLocation(updatedDetails.getLocation());
        restaurantDetailsRepository.save(details);
    }

    private Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + id));
    }

    private void validateRestaurantRequest(RestaurantRegisterRequestDto request) {
        validateRestaurantNameUniqueness(request.getRestaurantName());
        validateBusinessHours(request.getOpenTime(), request.getCloseTime());
        validateDeliveryTimeFormat(request.getEstimatedDeliveryTime());
    }

    private void validateRestaurantNameUniqueness(String restaurantName) {
        if (restaurantRepository.existsByRestaurantNameIgnoreCase(restaurantName)) {
            throw new BusinessException("Restaurant name already exists: " + restaurantName);
        }
    }

    private void validateRestaurantNameUniquenessForUpdate(String name, Long id) {
        Optional<Restaurant> existing = restaurantRepository.findByRestaurantNameIgnoreCase(name);
        if (existing.isPresent() && !existing.get().getRestaurantId().equals(id)) {
            throw new IllegalArgumentException("Another restaurant already uses the name: " + name);
        }
    }

    private void validateBusinessHours(String openTime, String closeTime) {
        if (openTime != null && closeTime != null) {
            try {
                LocalTime open = LocalTime.parse(openTime, TIME_FORMATTER);
                LocalTime close = LocalTime.parse(closeTime, TIME_FORMATTER);

                if (open.equals(close)) {
                    throw new BusinessException("Open time and close time cannot be the same");
                }

                // Note: We allow overnight operation (close < open)
                log.debug("Business hours validated: {} - {}", openTime, closeTime);
            } catch (DateTimeParseException e) {
                throw new BusinessException("Invalid time format. Use HH:MM format");
            }
        }
    }

    private void validateDeliveryTimeFormat(String deliveryTime) {
        if (deliveryTime != null && !deliveryTime.trim().isEmpty()) {
            Matcher matcher = DELIVERY_TIME_PATTERN.matcher(deliveryTime.trim());
            if (!matcher.matches()) {
                throw new BusinessException("Invalid delivery time format. Use format like '30 min' or '2 hours'");
            }
        }
    }

    private List<Category> validateAndGetCategories(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return null;
        }

        Set<Long> uniqueCategoryIds = removeDuplicateCategoryIds(categoryIds);
        List<Category> categories = fetchCategoriesById(uniqueCategoryIds);
        validateAllCategoriesExist(uniqueCategoryIds, categories);

        return categories;
    }

    private Set<Long> removeDuplicateCategoryIds(List<Long> categoryIds) {
        Set<Long> uniqueCategoryIds = new HashSet<>(categoryIds);
        if (uniqueCategoryIds.size() != categoryIds.size()) {
            log.warn("Duplicate category IDs found in request, removing duplicates");
        }
        return uniqueCategoryIds;
    }

    private List<Category> fetchCategoriesById(Set<Long> categoryIds) {
        return categoryRepository.findAllByIdIn(categoryIds);
    }

    private void validateAllCategoriesExist(Set<Long> requestedIds, List<Category> foundCategories) {
        if (foundCategories.size() != requestedIds.size()) {
            Set<Long> foundIds = foundCategories.stream()
                    .map(Category::getCategoryId)
                    .collect(Collectors.toSet());

            Set<Long> missingIds = requestedIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());

            throw new EntityNotFoundException("Categories not found: " + missingIds);
        }
    }

    private Restaurant createAndSaveRestaurant(RestaurantRegisterRequestDto request) {
        Restaurant restaurant = restaurantMapper.toEntity(request);
        return restaurantRepository.save(restaurant);
    }

    private RestaurantDetails createAndSaveRestaurantDetails(Restaurant restaurant, RestaurantRegisterRequestDto request) {
        RestaurantDetails details = buildRestaurantDetails(restaurant, request);
        return restaurantDetailsRepository.save(details);
    }

    private RestaurantDetails buildRestaurantDetails(Restaurant restaurant, RestaurantRegisterRequestDto request) {
        RestaurantDetails details = new RestaurantDetails();
        details.setRestaurant(restaurant);
        details.setDescription(request.getDescription());
        details.setPhone(request.getPhone());

        if (request.getEstimatedDeliveryTime() != null) {
            Duration deliveryDuration = parseDeliveryTimeToDuration(request.getEstimatedDeliveryTime());
            details.setEstimatedDeliveryTime(deliveryDuration);
        }

        if (request.getOpenTime() != null) {
            details.setOpenTime(LocalTime.parse(request.getOpenTime(), TIME_FORMATTER));
        }
        if (request.getCloseTime() != null) {
            details.setCloseTime(LocalTime.parse(request.getCloseTime(), TIME_FORMATTER));
        }

        Point point = createLocationPoint(request.getLongitude(), request.getLatitude());
        details.setLocation(point);

        return details;
    }

    private Duration parseDeliveryTimeToDuration(String deliveryTime) {
        Matcher matcher = DELIVERY_TIME_PATTERN.matcher(deliveryTime.trim());
        if (!matcher.matches()) {
            throw new BusinessException("Invalid delivery time format");
        }

        int value = Integer.parseInt(matcher.group(1));
        String unit = matcher.group(2).toLowerCase();

        return switch (unit) {
            case "min", "minutes" -> Duration.ofMinutes(value);
            case "hour", "hours" -> Duration.ofHours(value);
            default -> throw new BusinessException("Unsupported time unit: " + unit);
        };
    }

    private Point createLocationPoint(Double longitude, Double latitude) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(4326);
        return point;
    }

    private void addCategoriesToRestaurant(Restaurant restaurant, List<Category> categories) {
        if (categories != null && !categories.isEmpty()) {
            Set<RestaurantCategory> restaurantCategories = categories.stream()
                    .map(category -> new RestaurantCategory(restaurant, category))
                    .collect(Collectors.toSet());
            restaurant.setRestaurantCategories(restaurantCategories);
        }
    }
}

