package persistence.position;

import domaine.portfolio.model.Position;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PositionMapper {

    private PositionMapper() {}

    public static Position toModel(PositionEntity entity) {
        return new Position(entity.getKey(), entity.getSize());
    }

    public static List<Position> toModel(Collection<PositionEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entities.stream().map(PositionMapper::toModel).toList());
    }

    public static List<PositionEntity> toEntity(List<Position> positions) {
        if (CollectionUtils.isEmpty(positions)) {
            return new ArrayList<>();
        }
        return positions.stream().map(PositionMapper::toEntity).toList();
    }

    public static PositionEntity toEntity(Position position) {
        if (position == null) {
            return null;
        }
        PositionEntity entity = new PositionEntity();
        entity.setKey(position.getSymbol());
        entity.setSize(position.getSize());
        return entity;
    }
}
