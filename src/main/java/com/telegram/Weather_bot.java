package com.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Weather_bot extends TelegramLongPollingBot {

    /**
     * This method runs when you receive message from Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {


        System.out.println(update.getMessage().getText());

        SendMessage message = new SendMessage();

        if (update.getMessage().getLocation() == null) {
            message.setText("Please send your location");
        } else {
            message.setText(sendWeather(update));
        }

        message.setChatId(update.getMessage().getChatId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return Keys.Bot_name;
    }

    @Override
    public String getBotToken() {
        return Keys.Telegram_Key;
    }

    private String sendWeather(Update update) {

        System.out.println(update.getMessage().getLocation());

        Weather weather = new Weather();

        String forecast = "The weather is ";

        try {

            weather.getRequestFromAPI(update.getMessage().getLocation().getLatitude(), update.getMessage().getLocation().getLongitude());

            Double temp =  weather.getTemp();

            forecast = forecast + temp.toString() + "C";


        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecast;
    }
}
