package by.bsuir.shop.entity;

import java.util.Date;

/**
 * Entity class {@code Review} is the class, that describes Review of this system.
 * It contains basic methods to deal with shop objects.
 */
public class Review {
    private User user;
    private Integer itemId;
    private String comment;
    private Integer rating;
    private Date reviewDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (user != null ? !user.equals(review.user) : review.user != null) return false;
        if (itemId != null ? !itemId.equals(review.itemId) : review.itemId != null) return false;
        if (comment != null ? !comment.equals(review.comment) : review.comment != null) return false;
        if (rating != null ? !rating.equals(review.rating) : review.rating != null) return false;
        return reviewDate != null ? reviewDate.equals(review.reviewDate) : review.reviewDate == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (reviewDate != null ? reviewDate.hashCode() : 0);
        return result;
    }


}
