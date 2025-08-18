package persistence.portfolio;

import domaine.portfolio.model.Portfolio;
import persistence.position.PositionMapper;

import java.util.UUID;

public class PortfolioMapper {

    private PortfolioMapper() {}

    public static Portfolio toModel(PortfolioEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Portfolio(entity.getId(),
                getAccountId(entity),
                entity.getName(),
                entity.getDescription(),
                PositionMapper.toModel(entity.getPositions())) ;
    }

    public static PortfolioEntity toEntity(Portfolio portfolio) {
        if (portfolio == null) {
            return null;
        }

        PortfolioEntity entity = new PortfolioEntity();
        entity.setId(portfolio.id());
        entity.setName(portfolio.name());
        entity.setDescription(portfolio.description());
        entity.setPositions(PositionMapper.toEntity(portfolio.positions()));
        return entity;
    }

    private static UUID getAccountId(PortfolioEntity entity) {
        return entity.getAccount() != null ? entity.getAccount().getId() : null;
    }

}
