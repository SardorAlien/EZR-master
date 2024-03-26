package com.sendi.v1.security.repo;

import com.sendi.v1.security.domain.User;
//import com.sendi.v1.security.domain.User_;
import com.sendi.v1.security.domain.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);

    List<User> findAllByAccountNonLockedAndLastModifiedTimeIsBefore(Boolean locked, LocalDateTime localDateTime);
    
    interface Specs {
        static Specification<User> byUsername(String username) {
            return (root, query, builder) ->
                    builder.equal(root.get(User_.username), username);
        }

        static Specification<User> byFirstName(String firstName) {
            return (root, query, builder) ->
                    builder.equal(root.get(User_.firstname), firstName);
        }

        static Specification<User> byLastName(String lastName) {
            return (root, query, builder) ->
                    builder.equal(root.get(User_.lastname), lastName);
        }

        static Specification<User> orderByCreatedOn(
                Specification<User> spec) {
            return (root, query, builder) -> {
                query.orderBy(builder.asc(root.get(User_.createdAt)));
                return spec.toPredicate(root, query, builder);
            };
        }
    }

}
