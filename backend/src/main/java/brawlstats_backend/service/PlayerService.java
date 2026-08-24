package brawlstats_backend.service;

import brawlstats_backend.client.BrawlStarsClient;
import brawlstats_backend.dto.BattleLogDto;
import brawlstats_backend.dto.BrawlerDto;
import brawlstats_backend.dto.PlayerComparisonDto;
import brawlstats_backend.dto.PlayerComparisonEntryDto;
import brawlstats_backend.dto.PlayerDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.ToDoubleFunction;

@Service
public class PlayerService {

    private final BrawlStarsClient brawlStarsClient;

    public PlayerService(BrawlStarsClient brawlStarsClient) {
        this.brawlStarsClient = brawlStarsClient;
    }

    public PlayerDto getPlayer(String playerTag) {
        String normalizedTag = normalizePlayerTag(playerTag);
        return brawlStarsClient.getPlayer(normalizedTag);
    }

    public BattleLogDto getBattleLog(String playerTag) {
        String normalizedTag = normalizePlayerTag(playerTag);
        return brawlStarsClient.getBattleLog(normalizedTag);
    }

    public List<PlayerDto> getPlayers(List<String> playerTags) {
        if (playerTags == null || playerTags.isEmpty()) {
            throw new IllegalArgumentException("At least one player tag is required");
        }

        if (playerTags.size() > 10) {
            throw new IllegalArgumentException("A maximum of 10 players can be compared");
        }

        List<String> normalizedTags = playerTags.stream()
                .map(this::normalizePlayerTag)
                .distinct()
                .toList();

        if (normalizedTags.size() != playerTags.size()) {
            throw new IllegalArgumentException("Duplicate player tags are not allowed");
        }

        return normalizedTags.stream()
                .map(brawlStarsClient::getPlayer)
                .toList();
    }

    public PlayerComparisonDto comparePlayers(List<String> playerTags) {
        List<PlayerDto> players = getPlayers(playerTags);

        Map<String, Integer> trophiesRanks =
                calculateRanks(players, PlayerDto::getTrophies);

        Map<String, Integer> highestTrophiesRanks =
                calculateRanks(players, PlayerDto::getHighestTrophies);

        Map<String, Integer> victories3vs3Ranks =
                calculateRanks(players, PlayerDto::getVictories3vs3);

        Map<String, Integer> soloVictoriesRanks =
                calculateRanks(players, PlayerDto::getSoloVictories);

        Map<String, Integer> duoVictoriesRanks =
                calculateRanks(players, PlayerDto::getDuoVictories);

        Map<String, Integer> rankedEloRanks =
                calculateRanks(players, PlayerDto::getRankedElo);

        Map<String, Integer> expLevelRanks =
                calculateRanks(players, PlayerDto::getExpLevel);

        Map<String, Integer> brawlerCountRanks =
                calculateRanks(players, this::getBrawlerCount);

        Map<String, Integer> averageBrawlerTrophiesRanks =
                calculateRanks(players, this::getAverageBrawlerTrophies);

        Map<String, Integer> highestBrawlerTrophiesRanks =
                calculateRanks(players, this::getHighestBrawlerTrophies);

        int playerCount = players.size();

        Map<String, Integer> overallPoints = new HashMap<>();

        for (PlayerDto player : players) {
            int points = 0;

            points += pointsForRank(trophiesRanks.get(player.getTag()), playerCount);
            points += pointsForRank(highestTrophiesRanks.get(player.getTag()), playerCount);
            points += pointsForRank(victories3vs3Ranks.get(player.getTag()), playerCount);
            points += pointsForRank(soloVictoriesRanks.get(player.getTag()), playerCount);
            points += pointsForRank(duoVictoriesRanks.get(player.getTag()), playerCount);
            points += pointsForRank(rankedEloRanks.get(player.getTag()), playerCount);
            points += pointsForRank(expLevelRanks.get(player.getTag()), playerCount);
            points += pointsForRank(brawlerCountRanks.get(player.getTag()), playerCount);
            points += pointsForRank(averageBrawlerTrophiesRanks.get(player.getTag()), playerCount);
            points += pointsForRank(highestBrawlerTrophiesRanks.get(player.getTag()), playerCount);

            overallPoints.put(player.getTag(), points);
        }

        Map<String, Integer> overallRanks = calculateOverallRanks(
                players,
                overallPoints
        );

        List<PlayerComparisonEntryDto> comparisonPlayers = players.stream()
                .map(player -> new PlayerComparisonEntryDto(
                        player.getTag(),
                        player.getName(),

                        overallRanks.get(player.getTag()),
                        overallPoints.get(player.getTag()),

                        player.getTrophies(),
                        trophiesRanks.get(player.getTag()),

                        player.getHighestTrophies(),
                        highestTrophiesRanks.get(player.getTag()),

                        player.getVictories3vs3(),
                        victories3vs3Ranks.get(player.getTag()),

                        player.getSoloVictories(),
                        soloVictoriesRanks.get(player.getTag()),

                        player.getDuoVictories(),
                        duoVictoriesRanks.get(player.getTag()),

                        player.getRankedElo(),
                        rankedEloRanks.get(player.getTag()),

                        player.getExpLevel(),
                        expLevelRanks.get(player.getTag()),

                        getBrawlerCount(player),
                        brawlerCountRanks.get(player.getTag()),

                        round(getAverageBrawlerTrophies(player)),
                        averageBrawlerTrophiesRanks.get(player.getTag()),

                        getHighestBrawlerTrophies(player),
                        highestBrawlerTrophiesRanks.get(player.getTag())
                ))
                .sorted(Comparator.comparingInt(PlayerComparisonEntryDto::getOverallRank))
                .toList();

        return new PlayerComparisonDto(comparisonPlayers);
    }

    private Map<String, Integer> calculateRanks(
            List<PlayerDto> players,
            ToDoubleFunction<PlayerDto> valueExtractor) {

        List<PlayerDto> sortedPlayers = players.stream()
                .sorted(
                        Comparator.comparingDouble(valueExtractor)
                                .reversed()
                )
                .toList();

        Map<String, Integer> ranks = new HashMap<>();

        int currentRank = 0;
        double previousValue = Double.NaN;

        for (int i = 0; i < sortedPlayers.size(); i++) {
            PlayerDto player = sortedPlayers.get(i);
            double value = valueExtractor.applyAsDouble(player);

            if (i == 0 || Double.compare(value, previousValue) != 0) {
                currentRank = i + 1;
            }

            ranks.put(player.getTag(), currentRank);
            previousValue = value;
        }

        return ranks;
    }

    private Map<String, Integer> calculateOverallRanks(
            List<PlayerDto> players,
            Map<String, Integer> overallPoints) {

        List<PlayerDto> sortedPlayers = players.stream()
                .sorted(
                        Comparator.comparingInt(
                                        (PlayerDto player) ->
                                                overallPoints.get(player.getTag())
                                )
                                .reversed()
                )
                .toList();

        Map<String, Integer> ranks = new HashMap<>();

        int currentRank = 0;
        Integer previousPoints = null;

        for (int i = 0; i < sortedPlayers.size(); i++) {
            PlayerDto player = sortedPlayers.get(i);
            int points = overallPoints.get(player.getTag());

            if (previousPoints == null || points != previousPoints) {
                currentRank = i + 1;
            }

            ranks.put(player.getTag(), currentRank);
            previousPoints = points;
        }

        return ranks;
    }

    private int pointsForRank(int rank, int playerCount) {
        return playerCount - rank + 1;
    }

    private int getBrawlerCount(PlayerDto player) {
        if (player.getBrawlers() == null) {
            return 0;
        }

        return player.getBrawlers().size();
    }

    private double getAverageBrawlerTrophies(PlayerDto player) {
        if (player.getBrawlers() == null || player.getBrawlers().isEmpty()) {
            return 0;
        }

        return player.getBrawlers()
                .stream()
                .mapToInt(BrawlerDto::getTrophies)
                .average()
                .orElse(0);
    }

    private int getHighestBrawlerTrophies(PlayerDto player) {
        if (player.getBrawlers() == null || player.getBrawlers().isEmpty()) {
            return 0;
        }

        return player.getBrawlers()
                .stream()
                .mapToInt(BrawlerDto::getHighestTrophies)
                .max()
                .orElse(0);
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private String normalizePlayerTag(String playerTag) {
        if (playerTag == null || playerTag.isBlank()) {
            throw new IllegalArgumentException("Player tag must not be empty");
        }

        String normalizedTag = playerTag
                .trim()
                .toUpperCase(Locale.ROOT);

        if (normalizedTag.startsWith("%23")) {
            normalizedTag = normalizedTag.substring(3);
        }

        if (normalizedTag.startsWith("#")) {
            normalizedTag = normalizedTag.substring(1);
        }

        if (normalizedTag.isBlank()) {
            throw new IllegalArgumentException("Player tag must not be empty");
        }

        if (!normalizedTag.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("Player tag contains invalid characters");
        }

        return "#" + normalizedTag;
    }
}