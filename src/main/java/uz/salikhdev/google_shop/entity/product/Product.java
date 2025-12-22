package uz.salikhdev.google_shop.entity.product;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_product_color",
                    foreignKeyDefinition = "FOREIGN KEY (color_id) REFERENCES color(id) ON DELETE SET NULL"
            )
    )
    private Color color;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_product_category",
                    foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL"

            )
    )
    private Category category;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_product_size",
                    foreignKeyDefinition = "FOREIGN KEY (size_id) REFERENCES size(id) ON DELETE SET NULL"
            )

    )
    private Size size;
}
