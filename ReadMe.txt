# About
This project is Aung Bar Lay Lottery Checker integrating with Facebook Bot.
It currently deployed in heroku and integrated with ထီတိုက္စို႔(https://web.facebook.com/aungbarlaylotterychecker) Facebook page.

# Technologies

It used Spring Boot.
And It also embedded fb-bot-boilarplate-java(https://github.com/hyurumi/fb-bot-boilarplate-java).

The developer should have knowledge about Facebook Send API and Webhook API.(https://developers.facebook.com/docs/messenger-platform)

# API URL
https://myanmarlottery.herokuapp.com/result?type=2.

There are two entry points to handle RESTful Service.
(1) FBResultController
(2) ResultController

FBResultController- it handles the RESTful request from Facebook.
ResultController- it handles the RESTful request from Web client.

FBResultController delegates the following parameters to FBResultService.
- က ၁၂၃၄၅၆,ခ ၂၁၂၃၁၂
- က ည ၁၂၃၄၅၆
- က ၁၂၃၄၅၆ ၁၂၃၄၅၉
- help or ?
- @ က ဘ ၁၂၃၄၅၆ or @ က ၁၂၃၃၄၂,ခ ၂၁၂၁၂၁ or @ က ၁၂၃၄၅၆ ၁၂၃၄၅၉ (register lottery number to notify for winning number after lottery result is out)
- excel file or csv file (for lottery result data and authorized senders can only send this file)

csv and excel file structure

lottery_type,noOfTime,resultDate,dataProvider,developer
character,number,prize,numericType(not included in parsing process),prizeType
