package domaine.portfolio.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record Portfolio(UUID id,
                        UUID accountId,
                        String name,
                        String description,
                        List<Position> positions) {

    public Portfolio(UUID accountId, String name, String description) {
        this(null, accountId, name, description, new ArrayList<>());
    }

    public void addPosition(Position position) {
        Optional<Position> currentPosition = this.positions.stream()
                .filter(p -> p.getKey().equals(position.getKey()))
                .findFirst();

        if (currentPosition.isPresent()) {
            currentPosition.get().addSize(position.getSize());
        } else {
            this.positions.add(position);
        }
    }
}
