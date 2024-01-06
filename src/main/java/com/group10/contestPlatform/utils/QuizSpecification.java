package com.group10.contestPlatform.utils;
import com.group10.contestPlatform.entities.Quiz;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuizSpecification {
	public static Specification<Quiz> quickSearch(final String name, final LocalDate dateStart, final LocalDate dateEnd){
		return new Specification<Quiz>() {
			@Override
			public Predicate toPredicate(Root<Quiz> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (StringUtils.isNotBlank(name)) {
					predicates.add(cb.like(cb.lower(cb.function("LOWER", String.class, root.<String>get("title"))),
							"%" + name.toLowerCase().trim() + "%"));
				}

				if (Objects.nonNull(dateStart) && Objects.nonNull(dateEnd)) {
					predicates.add(
							cb.or(
									cb.and(
											cb.greaterThanOrEqualTo(root.get("startAt"), dateStart),
											cb.lessThanOrEqualTo(root.get("startAt"), dateEnd)
									),
									cb.and(
											cb.greaterThanOrEqualTo(root.get("endAt"), dateStart),
											cb.lessThanOrEqualTo(root.get("endAt"), dateEnd)
									)
							));
				}


				/**
				 * Kết hợp các điều kiện trong danh sách predicates thành một
				 * điều kiện tìm kiếm duy nhất sử dụng logic AND
				 */
				return cb.and(predicates.toArray(new Predicate[]{}));
			}
		};
	}
}
