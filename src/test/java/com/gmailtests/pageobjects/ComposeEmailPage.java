package com.gmailtests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ComposeEmailPage {
    private WebDriver _webDriver;

    private By _toLocator;
    private By _subjectLocator;
    private By _frameLocator;
    private By _bodyLocator;
    private By _composeEmailMenuLocator;
    private By _checkSpellingButtonLocator;
    private By _spellingErrorsLocator;

    private String _id;
    public String GetId()
    {
        return _id;
    }

    public ComposeEmailPage(WebDriver webDriver,String id)
    {
        _webDriver = webDriver;
        _id = "div[aria-labelledby=\""+id+"\"]";
        WebDriverWait wait = new WebDriverWait(_webDriver,50);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(_id+" .vO")));

        _toLocator = By.cssSelector(_id+" .vO");
        _subjectLocator = By.cssSelector(_id+" input[name=\"subjectbox\"]");
        _frameLocator = By.cssSelector(_id+" .Am.Al.editable iframe");
        _bodyLocator = By.cssSelector(".editable.LW-avf");
        _spellingErrorsLocator = By.cssSelector(".J-JK9eJ-PJVNOc");
        _composeEmailMenuLocator = By.cssSelector(_id+" .J-JN-M-I.J-J5-Ji.Xv.L3.T-I-ax7.T-I");
        _checkSpellingButtonLocator = By.cssSelector(_id+" .SK.AX .J-N:last-child");
    }
    public ComposeEmailPage To(String to) {
        GetToInput().sendKeys(to);
        return this;
    }

    public ComposeEmailPage WithSubject(String subject) {
        GetSubjectInput().sendKeys(subject);
        return this;
    }

    public ComposeEmailPage WithBody(String bodyText) {
        WebElement frame = _webDriver.findElement(_frameLocator);
        _webDriver.switchTo().frame(frame);
        WebElement body = _webDriver.findElement(_bodyLocator);
        body.sendKeys(bodyText);
        _webDriver.switchTo().defaultContent();
        return this;
    }

    public String GetRecipients() {
        List<WebElement> emailListWebElements = _webDriver.findElements(By.cssSelector(_id+" .oL.aDm.az9 span"));
        StringBuilder emailListBuilder = new StringBuilder();
        for(int i =0; i < emailListWebElements.size(); i++)
        {
            if(i != 0)
                emailListBuilder.append(", ");
            emailListBuilder.append(emailListWebElements.get(0).getText());
        }

        return emailListBuilder.toString();
    }

    public String GetSubject() {
        return GetSubjectInput().getAttribute("value");
    }

    public String GetEmailBody() {
        WebElement frame = _webDriver.findElement(_frameLocator);
        _webDriver.switchTo().frame(frame);
        WebElement body = _webDriver.findElement(_bodyLocator);
        String bodyText = body.getText();
        _webDriver.switchTo().defaultContent();
        return bodyText;
    }
    public boolean IsSpellCheckInMenu()
    {
        WebElement checkSpellMenu = _webDriver.findElement(_checkSpellingButtonLocator);
        return checkSpellMenu != null && checkSpellMenu.getText().equals("Check spelling");
    }
    public ComposeEmailPage ClickComposeEmailMenu() {
        _webDriver.findElement(_composeEmailMenuLocator).click();
        return this;
    }

    public ComposeEmailPage ClickCheckSpelling(){
        _webDriver.findElement(_checkSpellingButtonLocator).click();
        return this;
    }
    private WebElement GetToInput()
    {
        return _webDriver.findElement(_toLocator);
    }
    private WebElement GetSubjectInput()
    {
        return _webDriver.findElement(_subjectLocator);
    }

    public List<String> GetSpellingErrors() {
        List<String> spellingErrorsStrings = new ArrayList<String>();
        WebElement frame = _webDriver.findElement(_frameLocator);
        _webDriver.switchTo().frame(frame);
        WebElement body = _webDriver.findElement(_bodyLocator);
        List<WebElement> spellingErrors = body.findElements(_spellingErrorsLocator);

        for(WebElement spellingError : spellingErrors)
        {
            spellingErrorsStrings.add(spellingError.getText());
        }
        _webDriver.switchTo().defaultContent();


        return spellingErrorsStrings;
    }
}
