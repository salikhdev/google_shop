package uz.salikhdev.google_shop.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_shop.entity.product.Color;
import uz.salikhdev.google_shop.entity.product.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {



}
