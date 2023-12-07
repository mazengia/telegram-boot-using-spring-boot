package com.maze.springtelegramboot;


import com.maze.springtelegramboot.Status.User;
import com.maze.springtelegramboot.Status.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;


@Log4j2
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String token;

    @Value("${bot.name}")
    private String name;

    private final UserServiceImpl shareholderService;

    @Override
    public String getBotUsername() {
        log.info(name);
        return name; // "EnatShareBot";
    }

    @Override
    public String getBotToken() {
        log.info(token);
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        Optional<User> statusByChatId = shareholderService.findStatusById(String.valueOf(update.getMessage().getChatId()));
        if (update.getMessage().getContact() != null) {
            text = update.getMessage().getContact().getPhoneNumber();
            log.info(update.getMessage().getChatId());
            if (statusByChatId.isEmpty()) {
                User chatUser = new User();
                chatUser.setId(String.valueOf(update.getMessage().getContact().getUserId()));
                chatUser.setUserName(String.valueOf(update.getMessage().getFrom().getUserName()));
                chatUser.setFirstName(update.getMessage().getContact().getFirstName());
                chatUser.setLastName(update.getMessage().getContact().getLastName());
                chatUser.setPhoneNumber(update.getMessage().getContact().getPhoneNumber());
                shareholderService.addStatus(chatUser);
                sendMessage.setText("ስልክ ቁጥርዎን ተቀብለናል እናመግናልን !!!");

            }
        }
        switch (text) {
            case "/start":
                KeyboardRow keyboardRowOne = new KeyboardRow();
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                if (statusByChatId.isEmpty()) {
                    KeyboardButton buttonYes = new KeyboardButton("ስልክ ቁጥሬን አጋራ", true,
                            false,
                            null);
                    keyboardRowOne.addAll(Arrays.asList(buttonYes));
                    keyboardMarkup.setResizeKeyboard(true);
                    keyboardMarkup.setOneTimeKeyboard(true);
                    keyboardMarkup.setKeyboard(Collections.singletonList(keyboardRowOne));
                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText("ወደ ሶፍትዌር ቡት እንኳን ደህና መጡ። ለመቀጠል *“ስልክ ቁጥሬን አጋራ”* የሚለውን ይምረጡ ");
                }
                 else {
                keyboardMarkup.setResizeKeyboard(true);
                keyboardMarkup.setOneTimeKeyboard(true);
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                keyboardMarkup.setKeyboard(Collections.singletonList(keyboardRowOne));
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                for (String option : getOptionsForMenu()) {
                    KeyboardButton button = new KeyboardButton(option);
                    keyboardRowOne.add(button);
                }
                keyboardRows.add(keyboardRowOne);
                keyboardMarkup.setKeyboard(keyboardRows);
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setReplyMarkup(keyboardMarkup);
                String message = "ከታች ከተዘረዘሩት የሚፈልጉትን ይምረጡ። እናመሰግናለን።";
                sendMessage.setText(message);
                }
                break;
            case "/option":
                sendMessage.setText("Please share your phone number to be able us \n" +
                        "maintain your dividend related options.\n" +
                        "By doing so you'll eliminate to need to appear\n" +
                        "at the head office.");
                break;
            default:
                switch (text) {
                    case "Dividend Payment":
                        sendMessage.setText("Here are the available options for dividend payment:");
                        break;
                    case "Shareholding Information":
                        sendMessage.setText("Here is your shareholding information:");
                        break;
                    case "Update Contact Information":
                        sendMessage.setText("Please share your phone number to update your contact information:");
                        break;
                }
                break;

                }

                try {
                    sendMessage.setParseMode("MarkdownV2");
                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                    execute(sendMessage); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
        }

        // Method to retrieve the list of options
        private List<String> getOptionsForMenu () {
            return Arrays.asList("ስልክ ቁጥሬን አጋራ","Dividend Payment", "Shareholding Information", "Update Contact Information");
        }
    }
