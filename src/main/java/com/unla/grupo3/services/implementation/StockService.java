package com.unla.grupo3.services.implementation;

import com.unla.grupo3.entities.Producto;
import com.unla.grupo3.entities.Stock;
import com.unla.grupo3.repositories.IProductoRepository;
import com.unla.grupo3.repositories.IStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("stockService")
public class StockService {

    private com.unla.grupo3.repositories.IStockRepository stockRepository;

    @Autowired
    private ProductoService productoService;

    public StockService(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> traerStocksOrdenados() {
        List<Stock> stocks = stockRepository.traerStocksConProducto();
        List<Stock> lowStock = new ArrayList<>();
        List<Stock> normalStock = new ArrayList<>();

        for (Stock stock : stocks) {
            if (stock.getCantExistente() < stock.getCantMinima()) {
                lowStock.add(stock);
            } else {
                normalStock.add(stock);
            }
        }

        lowStock.addAll(normalStock);
        return lowStock;
    }

    public void eliminarStock(int id) {
        stockRepository.deleteById(id);
    }

    public Stock traerPorId(int id) {
        return stockRepository.traerPorId(id);
    }

    @Transactional
    public void actualizarProducto(Stock stock) {
        stockRepository.actualizarStock(stock.getId(), stock.getCantExistente(), stock.getCantMinima());
    }
}
