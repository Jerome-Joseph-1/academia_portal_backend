package com.academia.arcademia_backend.repositories;

import com.academia.arcademia_backend.entity.Course;
import com.academia.arcademia_backend.entity.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {


    @Query("SELECT cs FROM CourseSchedule cs WHERE cs.course = :course")
    List<CourseSchedule> findAllByCourse(@Param("course") Course course);

    boolean existsByDayAndTimeAndRoom(String day, String time, String room);

}
