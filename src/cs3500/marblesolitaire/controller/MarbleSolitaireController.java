package cs3500.marblesolitaire.controller;

public interface MarbleSolitaireController {

    /**
     * Plays a new game of English Marble Solitaire
     *
     * @throws IllegalStateException only if the controller is unable to successfully read input
     *                               or transmit output
     */
    void playGame() throws IllegalStateException;
}
