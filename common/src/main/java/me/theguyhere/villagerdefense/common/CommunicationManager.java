package me.theguyhere.villagerdefense.common;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class CommunicationManager {
    @Setter
    @Getter
    private static DebugLevel debugLevel = DebugLevel.QUIET;
    
    /**
     * Translates color codes that use "&" into their proper form to be displayed by Bukkit.
     *
     * @param msg Message to be translated.
     * @return Properly translated message.
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * Formats a long description into an array of colored lines, split according to the set character limit.
     *
     * @param base Base color for the description.
     * @param description The description to be formatted.
     * @param charLimit The character limit per line.
     * @return Formatted description.
     */
    public static String[] formatDescriptionArr(ChatColor base, String description, int charLimit) {
        return formatDescriptionList(base, description, charLimit).toArray(new String[0]);
    }

    /**
     * Formats a long description into a list of colored lines, split according to the set character limit.
     *
     * @param base Base color for the description.
     * @param description The description to be formatted.
     * @param charLimit The character limit per line.
     * @return Formatted description.
     */
    public static List<String> formatDescriptionList(ChatColor base, String description, int charLimit) {
        // Get a proper ChatColor base to work with
        ChatColor properBase = base == null ? ChatColor.WHITE : base;

        // The visible character limit per line
        int realCharLimit = charLimit + properBase.toString().length();

        // Split the description into words
        String[] descArray = description.split(" ");

        List<String> descLines = new ArrayList<>();
        StringBuilder line = new StringBuilder(properBase.toString());

        for (String s : descArray) {
            // Always add to a line if empty or line remains under character limit
            if (line.length() == 0 || line.length() + s.length() <= realCharLimit)
                line.append(s).append(" ");

            // Start new line if next word makes line longer than character limit
            else {
                line.deleteCharAt(line.length() - 1);
                descLines.add(format(line.toString()));
                line = new StringBuilder(properBase.toString()).append(s).append(" ");
            }
        }

        // Add last line
        line.deleteCharAt(line.length() - 1);
        descLines.add(line.toString());

        return descLines;
    }

    /**
     * Replaces placeholders in the base colored message with colored replacements.
     *
     * @param base Base colored message.
     * @param replacements Replacement colored messages.
     * @return Properly formatted combined colored message.
     */
    public static String format(ColoredMessage base, ColoredMessage... replacements) {
        try {
            String formattedString = base.toString();
            for (ColoredMessage replacement : replacements) {
                formattedString = formattedString.replaceFirst("%s",
                        replacement.getBase() + replacement.getMessage() + base.getBase());
            }
            return formattedString;
        } catch (IllegalFormatException e) {
            debugError(DebugLevel.QUIET, "The number of replacements is likely incorrect when formatting a message!",
                true, e);
        } catch (Exception e) {
            debugError(DebugLevel.QUIET, "Something unexpected happened when formatting messages!", true, e);
        }

        return "";
    }

    /**
     * Replaces placeholders in the base colored message with aqua colored replacements.
     *
     * @param base Base colored message.
     * @param replacements Replacement messages.
     * @return Properly formatted combined colored message.
     */
    public static String format(ColoredMessage base, String... replacements) {
        try {
            String formattedString = base.toString();
            for (String replacement : replacements)
                formattedString = formattedString.replaceFirst("%s",
                        ChatColor.AQUA + replacement + base.getBase());
            return formattedString;
        } catch (IllegalFormatException e) {
            debugError(DebugLevel.QUIET, "The number of replacements is likely incorrect when formatting a message!",
                true, e);
        } catch (Exception e) {
            debugError(DebugLevel.QUIET, "Something unexpected happened when formatting messages!", true, e);
        }

        return "";
    }

    /**
     * Formats plugin notifications to players.
     *
     * @param msg Raw message to send to player.
     * @return Formatted message prepared to be sent to the player.
     */
    public static String notify(String msg) {
        return format("&2[VD] &f" + msg);
    }

    /**
     * Formats plugin notifications from entities with names.
     *
     * @param name Colored name of the entity.
     * @param msg Raw message to send to player.
     * @return Formatted message prepared to be sent to the player.
     */
    public static String namedNotify(ColoredMessage name, String msg) {
        return format("&2[VD] " + name.toString() + ":&f " + msg);
    }

    public static void debugError(DebugLevel debugLevel, String msg) {
        debugError(debugLevel, msg, false);
    }

    public static void debugError(DebugLevel debugLevel, String msg, boolean stackTrace) {
        if (CommunicationManager.debugLevel.atLeast(debugLevel)) {
            ConsoleManager.warning(msg);

            if (stackTrace)
                Thread.dumpStack();
        }
    }

    public static void debugError(DebugLevel debugLevel, String msg, boolean stackTrace, Exception e) {
        if (CommunicationManager.debugLevel.atLeast(debugLevel)) {
            ConsoleManager.warning(msg);

            if (stackTrace)
                e.printStackTrace();
        }
    }

    public static void debugError(DebugLevel debugLevel, String base, String... replacements) {
        debugError(debugLevel, base, false, replacements);
    }

    public static void debugError(DebugLevel debugLevel, String base, boolean stackTrace, String... replacements) {
        if (CommunicationManager.debugLevel.atLeast(debugLevel)) {
            String formattedMessage = base;
            for (String replacement : replacements)
                formattedMessage = formattedMessage.replaceFirst("%s",
                        ChatColor.BLUE + replacement + ChatColor.RED);
            ConsoleManager.warning(formattedMessage);

            if (stackTrace)
                Thread.dumpStack();
        }
    }

    public static void debugErrorShouldNotHappen() {
        debugError(DebugLevel.QUIET, "This should not be happening!", true);
    }

    public static void debugInfo(DebugLevel debugLevel, String msg) {
        debugInfo(debugLevel, msg, false);
    }

    public static void debugInfo(DebugLevel debugLevel, String msg, boolean stackTrace) {
        if (CommunicationManager.debugLevel.atLeast(debugLevel)) {
            ConsoleManager.info(msg);

            if (stackTrace)
                Thread.dumpStack();
        }
    }

    public static void debugInfo(DebugLevel debugLevel, String base, String... replacements) {
        debugInfo(debugLevel, base, false, replacements);
    }

    public static void debugInfo(DebugLevel debugLevel, String base, boolean stackTrace, String... replacements) {
        if (CommunicationManager.debugLevel.atLeast(debugLevel)) {
            String formattedMessage = base;
            for (String replacement : replacements)
                formattedMessage = formattedMessage.replaceFirst("%s",
                        ChatColor.BLUE + replacement + ChatColor.WHITE);
            ConsoleManager.info(formattedMessage);

            if (stackTrace)
                Thread.dumpStack();
        }
    }

    public static void debugConfirm(DebugLevel debugLevel, String msg) {
        if (CommunicationManager.debugLevel.atLeast(debugLevel))
            ConsoleManager.confirm(msg);
    }

    public enum DebugLevel {
        /**
         * Only the most urgent error messages will be displayed.
         */
        QUIET(0),
        /**
         * Messages for annoying errors and important information will be displayed.
         */
        NORMAL(1),
        /**
         * All errors and information tracked will be displayed.
         */
        VERBOSE(2),
        /**
         * All errors and information tracked will be displayed. Certain behavior will be overridden.
         */
        DEVELOPER(3);

        private final int level;

        DebugLevel(int level) {
            this.level = level;
        }

        public boolean atLeast(DebugLevel level) {
            return this.level >= level.level;
        }

        public boolean atMost(DebugLevel level) {
            return this.level <= level.level;
        }
    }
}
