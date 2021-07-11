package com.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

@Entity
@Table(name="file_details")
@Data
public class FileDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_details_id",nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long size;

    @Lob
    @Column(name = "brand_images", nullable = false, columnDefinition = "mediumblob")
    private byte[] images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = true)
    @JsonBackReference
    private Electronics electronics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getImages() {
        String encodeBase64 = Base64.getEncoder().encodeToString(this.images);
        String image = "data:" + extension + ";base64," + encodeBase64;
        return image;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public Electronics getElectronics() {
        return electronics;
    }

    public void setElectronics(Electronics electronics) {
        this.electronics = electronics;
    }

    @Override
    public String toString() {
        return "FileDetails{" +
                "id=" + id +
                ", originalFileName='" + originalFileName + '\'' +
                ", extension='" + extension + '\'' +
                ", size=" + size +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
