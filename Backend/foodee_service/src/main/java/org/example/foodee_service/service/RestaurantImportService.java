package org.example.foodee_service.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.entity.Category;
import org.example.foodee_service.entity.Restaurant;
import org.example.foodee_service.repository.CategoryRepository;
import org.example.foodee_service.repository.RestaurantRepository;
import org.example.foodee_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantImportService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public void importFromCsv(InputStream inputStream, Long categoryId) throws IOException {
        List<Category> categories = categoryRepository.findAll();
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] line;
//            List<String[]> allLines = reader.readAll();  // Đọc tất cả dòng
//            int totalLines = allLines.size();  // Tổng số dòng trong file CSV
//            int processedLines = 0;
            reader.readNext(); // Bỏ dòng tiêu đề

            while ((line = reader.readNext()) != null) {
                Category c = null;
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getCategoryId().equals(parseLong(line[82]))) {
                        c = categories.get(i);
                        break;
                    }
                }
                if (restaurantRepository.existsById(parseLong(line[0]))) {
                    Restaurant existingRestaurant = restaurantRepository.getByRestaurantId(parseLong(line[0]));
                    String[] finalLine = line;
                    existingRestaurant.getCategory().add(c);
                    restaurantRepository.save(existingRestaurant);
                }else {
                    Restaurant restaurant = Restaurant.builder()
                            .restaurantId(parseLong(line[0])) // Id
                            .restaurantName(line[1]) // Name
                            .restaurantAddress(line[2]) // Address
                            .restaurantAvgRating(parseBigDecimal(line[3]))
                            .restaurantAvgRatingText(line[4])
                            .restaurantStatus(parseInt(line[5]))
                            .restaurantPhone("Đang cập nhật".equalsIgnoreCase(line[6]) ? null : line[6])
                            .restaurantPhotoUrl(line[7])
                            .restaurantDescription(line[15])
                            .restaurantLatitude(parseDouble(line[26]))
                            .restaurantLongitude(parseDouble(line[27]))
                            .restaurantIsBooking(Boolean.parseBoolean(line[64]))
                            .restaurantIsDelivery(Boolean.parseBoolean(line[67]))
                            .restaurantIsOpening(Boolean.parseBoolean(line[77]))
                            .restaurantIsAds(Boolean.parseBoolean(line[62]))
                            .build();
                    if (c != null) {
                        restaurant.setCategory(new HashSet<>(List.of(c)));
                    }

                    restaurantRepository.save(restaurant);
                }
            }
        } catch (CsvValidationException e) {
            log.error("CSV validation error: {}", e.getMessage());
        } catch (CsvException e) {
            log.error("CSV parsing error: {}", e.getMessage());
        }
    }


    private Long parseLong(String s) {
        return Long.parseLong(s.trim());
    }

    private Object parseLongOrNull(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String s) {
        try {
            return new BigDecimal(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseDateTime(String s) {
        try {
            return LocalDateTime.parse(s.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
