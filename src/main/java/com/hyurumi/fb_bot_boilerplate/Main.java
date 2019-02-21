/**
The MIT License (MIT)

Copyright (c) 2016 Furumi Genki

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.hyurumi.fb_bot_boilerplate;

import com.google.gson.Gson;
import com.hyurumi.fb_bot_boilerplate.models.common.Action;
import com.hyurumi.fb_bot_boilerplate.models.send.Button;
import com.hyurumi.fb_bot_boilerplate.models.send.Element;
import com.hyurumi.fb_bot_boilerplate.models.send.Message;
import com.hyurumi.fb_bot_boilerplate.models.webhook.Messaging;
import com.hyurumi.fb_bot_boilerplate.models.webhook.ReceivedMessage;
import okhttp3.*;

import java.util.List;
import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

public class Main {
    public static  String sAccessToken;
    private static String sValidationToken;
    public static final String END_POINT;
    public static final MediaType JSON;
    private static final Random sRandom;
    private static final Gson GSON;
    private static final String PORT = "8181";

    static {
        JSON = MediaType.parse("application/json; charset=utf-8");
        END_POINT = "https://graph.facebook.com/v2.6/me/messages";
        GSON = new Gson();
        sRandom = new Random();
        sAccessToken = "EAAC6CoQsmOQBALDgvNuFAuEytTOZBjrUJVUodqGZBF620fInZByyBDqfx4A6XvDgnxIgXGZAX1nv2WJJSKVxolgPeNF2kdbGqvdp9h6wtGMCwecryr5bL4G7SPPbBWmbl1kWruiPIWJkdoed9cdgTyO5BrfZAzgWIdsmFK9dZCWgZDZD";//System.getenv("ACCESS_TOKEN");
        sValidationToken = "EAAC6CoQsmOQBALDgvNuFAuEytTOZBjrUJVUodqGZBF620fInZByyBDqfx4A6XvDgnxIgXGZAX1nv2WJJSKVxolgPeNF2kdbGqvdp9h6wtGMCwecryr5bL4G7SPPbBWmbl1kWruiPIWJkdoed9cdgTyO5BrfZAzgWIdsmFK9dZCWgZDZD";//System.getenv("VALIDATION_TOKEN");
    }

    public static void main(String[] args) {

        port(Integer.valueOf(PORT)); //System.getenv("PORT")

        get("/webhook", (request, response) -> {
            if (request.queryMap("hub.verify_token").value().equals(sValidationToken)) {
                return request.queryMap("hub.challenge").value();
            }
            return "Error, wrong validation token";
        });

        post("/webhook", (request, response) -> {
            ReceivedMessage receivedMessage = GSON.fromJson(request.body(), ReceivedMessage.class);
            List<Messaging> messagings = receivedMessage.entry.get(0).messaging;
            for (Messaging messaging : messagings) {
                String senderId = messaging.sender.id;
                if (messaging.message !=null) {
                    // Receiving text message
                    switch (sRandom.nextInt(4)){
                        case 0:
                            if (messaging.message.text != null)
                                Message.Text(messaging.message.text).sendTo(senderId);
                            else
                                sendSampleGenericMessage(senderId);
                            break;
                        case 1:
                            Message.Image("https://unsplash.it/764/400?image=200").sendTo(senderId);
                            break;
                        case 2:
                            sendSampleGenericMessage(senderId);
                            break;
                        default:
                            sendSamplePostBackMessage(senderId);
                            break;
                    }

                } else if (messaging.postback != null) {
                    // Receiving postback message
                    if (messaging.postback.payload == Action.ACTION_A) {
                        Message.Text("Action A").sendTo(senderId);
                    }else {
                        Message.Text("Action B").sendTo(senderId);
                    }
                } else if (messaging.delivery != null) {
                    // when the message is delivered, this webhook will be triggered.
                } else {
                    // sticker may not be supported for now.
                    System.out.println(request.body());
                }
            }
            System.out.println(request.body());
            return "";
        });
    }

    static private void sendSamplePostBackMessage(String senderId) throws Exception {
        Message message = Message.Button("This is a postback message; please choose the action below");
        message.addButton(Button.Postback("action A", Action.ACTION_A));
        message.addButton(Button.Postback("action B", Action.ACTION_B));
        message.addButton(Button.Url("open Google", "https://google.com"));
        message.sendTo(senderId);
    }

    static private void sendSampleGenericMessage(String senderId) throws Exception {
        Message message = Message.Generic();
        Element element = new Element("Generic Message Sample", "https://unsplash.it/764/400?image=400", "subtitle");
        message.addElement(element);
        element = new Element("Yay Yay", "https://unsplash.it/764/400?image=500", "subtitle");
        element.addButton(Button.Postback("action A", Action.ACTION_A));
        element.addButton(Button.Url("jump to FB", "https://facebook.com/"));
        message.addElement(element);
        message.sendTo(senderId);
    }
}