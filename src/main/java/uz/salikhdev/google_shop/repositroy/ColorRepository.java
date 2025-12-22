package uz.salikhdev.google_shop.repositroy;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_shop.entity.product.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    boolean existsByName(@NotBlank String name);
}
