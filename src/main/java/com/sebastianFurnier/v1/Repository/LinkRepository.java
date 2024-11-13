package com.sebastianFurnier.v1.Repository;

import com.sebastianFurnier.v1.Model.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<ShortLink, String> {
    public Optional<ShortLink> findByUrl(String shortId);
}
