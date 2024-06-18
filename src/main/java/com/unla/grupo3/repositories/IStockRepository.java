package com.unla.grupo3.repositories;

import com.unla.grupo3.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository("stockRepository")
public interface IStockRepository extends JpaRepository<Stock, Serializable> {

    @Query("SELECT s FROM Stock s JOIN FETCH s.producto p WHERE p.codigo = (:codigo)")
    public abstract Stock findByCodigoAndFetchProductoEagerly(@Param("codigo") String codigo);

    @Query("SELECT s FROM Stock s JOIN FETCH s.producto")
    public abstract List<Stock> traerStocksConProducto();

    @Query("SELECT s FROM Stock s JOIN FETCH s.producto where s.id = (:id)")
    public abstract Stock traerPorId(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.cantExistente = :cantExistente, s.cantMinima = :cantMinima WHERE s.id = :id")
    void actualizarStock(@Param("id") int id, @Param("cantExistente") int cantExistente, @Param("cantMinima") int cantMinima);

}