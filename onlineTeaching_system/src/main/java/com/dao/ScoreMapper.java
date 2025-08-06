package com.dao;

import com.model.Score;

public interface ScoreMapper {
    int deleteScoreBySCid(Integer scId);

    int addScore(Score score);

    Score selectScoreBySCid(Integer scId);

    int updateScoreBySCid(Score score);

}