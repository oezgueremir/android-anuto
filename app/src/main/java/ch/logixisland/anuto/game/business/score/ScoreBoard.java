package ch.logixisland.anuto.game.business.score;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private int mCredits;
    private int mCreditsEarned;
    private int mScore;
    private int mLives;
    private int mEarlyBonus;
    private int mWaveBonus;

    private final List<CreditsListener> mCreditsListeners = new ArrayList<>();
    private final List<LivesListener> mLivesListeners = new ArrayList<>();
    private final List<BonusListener> mBonusListeners = new ArrayList<>();

    public synchronized void reset(int lives, int credits) {
        mCredits = credits;
        mCreditsEarned = credits;
        mScore = 0;
        mLives = lives;
        mEarlyBonus = 0;
        mWaveBonus = 0;

        creditsChanged();
        livesChanged();
        bonusChanged();
    }

    public synchronized void addScore(int score) {
        mScore += score;
    }

    public synchronized void takeLives(int lives) {
        mLives -= lives;
        livesChanged();
    }

    public synchronized void giveCredits(int credits) {
        mCredits += credits;
        mCreditsEarned += credits;
        creditsChanged();
    }

    public synchronized void reimburseCredits(int credits) {
        mCredits += credits;
        creditsChanged();
    }

    public synchronized void takeCredits(int credits) {
        mCredits -= credits;
        creditsChanged();
    }

    public synchronized void setEarlyBonus(int earlyBonus) {
        mEarlyBonus = earlyBonus;
        bonusChanged();
    }

    public synchronized void setWaveBonus(int waveBonus) {
        mWaveBonus = waveBonus;
        bonusChanged();
    }

    public int getCredits() {
        return mCredits;
    }

    public int getCreditsEarned() {
        return mCreditsEarned;
    }

    public int getScore() {
        return mScore;
    }

    public int getLives() {
        return mLives;
    }

    public int getEarlyBonus() {
        return mEarlyBonus;
    }

    public void addBonusListener(BonusListener listener) {
        mBonusListeners.add(listener);
    }

    public void removeBonusListener(BonusListener listener) {
        mBonusListeners.remove(listener);
    }

    public void addCreditsListener(CreditsListener listener) {
        mCreditsListeners.add(listener);
    }

    public void removeCreditsListener(CreditsListener listener) {
        mCreditsListeners.remove(listener);
    }

    public void addLivesListener(LivesListener listener) {
        mLivesListeners.add(listener);
    }

    public void removeLivesListener(LivesListener listener) {
        mLivesListeners.remove(listener);
    }

    private void bonusChanged() {
        for (BonusListener listener : mBonusListeners) {
            listener.bonusChanged(mWaveBonus, mEarlyBonus);
        }
    }

    private void creditsChanged() {
        for (CreditsListener listener : mCreditsListeners) {
            listener.creditsChanged(mCredits);
        }
    }

    private void livesChanged() {
        for (LivesListener listener : mLivesListeners) {
            listener.livesChanged(mLives);
        }
    }
}