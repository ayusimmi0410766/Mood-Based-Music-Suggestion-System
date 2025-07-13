public class Mood {
    public String mood;
    public String suggestion;

    public Mood(String mood) {
        this.mood = mood.toLowerCase();
        this.suggestion = generateSuggestion();
    }

    private String generateSuggestion() {
        switch (mood) {
            case "happy":
                return "<a href='https://www.youtube.com/watch?v=ZbZSe6N_BXs'>🎵 Happy – Pharrell Williams</a>";
            case "sad":
                return "<a href='https://www.youtube.com/watch?v=RB-RcX5DS5A'>💔 Someone Like You – Adele</a>";
            case "energetic":
                return "<a href='https://www.youtube.com/watch?v=fLexgOxsZu0'>⚡ Can’t Hold Us – Macklemore</a>";
            case "calm":
                return "<a href='https://www.youtube.com/watch?v=2OEL4P1Rz04'>🌊 Weightless – Marconi Union</a>";
            default:
                return "Mood not recognized.";
        }
    }

    public String toHTML() {
        return "<h2>Your Mood: " + mood + "</h2><p>" + suggestion + "</p>";
    }
}
