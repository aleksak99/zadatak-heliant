package rs.heliant.zadatak.repository;

import org.openapitools.model.PopunjenFormularDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.heliant.zadatak.entity.Formular;
import rs.heliant.zadatak.entity.FormularPopunjen;

import java.util.List;

@Repository
public interface FormularPopunjenRepository extends JpaRepository<FormularPopunjen, Integer> {

    @Query(value = "SELECT fp FROM FormularPopunjen fp JOIN FETCH fp.popunjenaPolja pp WHERE fp.formular=:formular")
    List<FormularPopunjen> pronadjiPopunjeneFormulare(@Param("formular") Formular formular, PageRequest pageRequest);
}
