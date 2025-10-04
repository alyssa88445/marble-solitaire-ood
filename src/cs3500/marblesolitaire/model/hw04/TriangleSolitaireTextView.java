package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

import java.io.IOException;
import java.util.Objects;

/**
 * Text view for the Triangle Solitaire game.
 */
public class TriangleSolitaireTextView implements MarbleSolitaireView {
    private final MarbleSolitaireModelState model;
    private final Appendable appendable;

    /**
     * Constructor using model only, output goes to System.out.
     *
     * @param model the model to render
     * @throws IllegalArgumentException if model is null
     */
    public TriangleSolitaireTextView(MarbleSolitaireModelState model) {
        this(model, System.out);
    }

    /**
     * Constructor using model and appendable.
     *
     * @param model the model to render
     * @param out the output destination
     * @throws IllegalArgumentException if model or out is null
     */
    public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable out) {
        if (model == null || out == null) {
            throw new IllegalArgumentException("Model and Appendable cannot be null");
        }
        this.model = model;
        this.appendable = out;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = model.getBoardSize();

        for (int r = 0; r < size; r++) {
            // Leading spaces to align triangle
            for (int s = 0; s < size - r - 1; s++) {
                sb.append(" ");
            }

            for (int c = 0; c <= r; c++) {
                switch (model.getSlotAt(r, c)) {
                    case Marble:
                        sb.append("O");
                        break;
                    case Empty:
                        sb.append("_");
                        break;
                    case Invalid:
                        sb.append(" ");
                        break;
                }
                if (c < r) {
                    sb.append(" ");
                }
            }

            if (r < size - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public void renderBoard() throws IOException {
        appendable.append(this.toString());
    }

    @Override
    public void renderMessage(String message) throws IOException {
        appendable.append(Objects.requireNonNull(message));
    }
}
