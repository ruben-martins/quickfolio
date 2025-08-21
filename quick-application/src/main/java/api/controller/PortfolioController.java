package api.controller;

import domaine.portfolio.model.CreatePortfolioRequest;
import domaine.portfolio.model.Portfolio;
import domaine.portfolio.model.Position;
import domaine.portfolio.service.PortfolioService;
import domaine.price.model.PricedPortfolio;
import domaine.price.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PriceService priceService;

    public PortfolioController(PortfolioService portfolioService, PriceService priceService) {
        this.portfolioService = portfolioService;
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity<Portfolio> getPortfolio(@RequestParam("accountId") UUID accountId,
                                                  @RequestParam("name") String name) {
        return portfolioService.findByName(accountId, name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody CreatePortfolioRequest createPortfolioRequest) {
        return ResponseEntity.ok(portfolioService.create(createPortfolioRequest));
    }

    @PutMapping
    public ResponseEntity<Portfolio> updatePortfolio(@RequestBody Portfolio portfolio) {
        return portfolioService.update(portfolio)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePortfolio(@RequestParam("accountId") UUID accountId,
                                                @RequestParam("name") String name) {
        portfolioService.delete(accountId, name);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addPosition")
    public ResponseEntity<Portfolio> addPositionToPortfolio(@RequestParam("portfolioId") UUID portfolioId,
                                                            @RequestBody Position position) {
        return ResponseEntity.ok(portfolioService.addPosition(portfolioId, position));
    }

    @GetMapping("/value")
    public ResponseEntity<List<PricedPortfolio>> getPortfolioPriced(@RequestParam("accountId") UUID accountId) {
        return ResponseEntity.ok(priceService.getPricedPortfolio(accountId));
    }

}
