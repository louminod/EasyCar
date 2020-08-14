package view;

public interface CustomFrame {
    /**
     * Build the frame with specified parameters.
     *
     * @param title  The frame title.
     * @param width  The frame width.
     * @param height The frame height.
     */
    void buildFrame(String title, int width, int height);

    /**
     * Build the JFrame components.
     */
    void buildComponents();

    /**
     * Show the frame.
     */
    void showFrame();

    /**
     * Hide the frame.
     */
    void hideFrame();

}
