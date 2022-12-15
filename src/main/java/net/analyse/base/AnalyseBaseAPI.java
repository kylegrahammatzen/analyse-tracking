package net.analyse.base;

import net.analyse.base.utils.player.AnalysePlayer;

import java.util.List;
import java.util.UUID;

public interface AnalyseBaseAPI {
    List<UUID> getOnlinePlayers();

    List<AnalysePlayer> getOnlineAnalysePlayers();


    AnalysePlayer getPlayer(UUID uuid);

    AnalysePlayer getPlayer(String name);

    List<AnalysePlayer> getPlayersByCountry(String country);

    List<AnalysePlayer> getPlayersByCountry(String country, int limit);

    List<AnalysePlayer> getPlayersByDomain(String domain);

    List<AnalysePlayer> getPlayersByDomain(String domain, int limit);
}
