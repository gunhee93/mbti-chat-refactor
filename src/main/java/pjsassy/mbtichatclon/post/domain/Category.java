package pjsassy.mbtichatclon.post.domain;

import lombok.Getter;

import javax.persistence.Entity;

@Entity @Getter
public class Category {

    private String free;
    private String meeting;
}
