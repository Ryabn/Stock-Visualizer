# Stock-Visualizer

Stock Portfolio Organizer with a Graphical user interface. 

#How are stock values taken?
A get request is made to 'https://api.iextrading.com/' with the parameters for the request
filled with user selections for the stock symbol and type.

#How is portofolio saved?

The user stock portfolio is saved by creating a local file that saves the user information and is parsed every 
time data is shown.

#Which Java libraries are being used?

MinimalJson - a json parser for java
JFreeChart - charting library for java swing GUIs
