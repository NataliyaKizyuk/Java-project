
package com.assignment.oop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
/**
 * Assignment. Topic: Robo-Reader
 * 30/03/2016
 * @author Nataliya Kizyuk
 * * *******************************
   Class RoboScreenActionListener that extends Class RoboScreen and implements 
 * Java Swing Interface ActionListenergets.
 * It gets input from the RoboScreen and sets output to the RoboScreen
 */
public class RoboScreenActionListener extends RoboScreen implements ActionListener
{
    //class attributes
    protected CheckTextDocName format;
    protected String docName = "";
    protected String exclWords = " ";
    protected String docDescription;
    protected JTextArea outputText;
    
    // class constructor 
    public RoboScreenActionListener(String title, String[] listOfTextDocs) 
    {
        super(title,listOfTextDocs);
        super.TEXRFIELD1.addActionListener(this);
        super.TEXTFIELD2.addActionListener(this);
        super.button1.addActionListener(this);
        super.button2.addActionListener(this);
        super.listOfDoc.addActionListener(this);
         //reference: https://docs.oracle.com/javase/tutorial/uiswing/components/textarea.html
        this.outputText = new JTextArea(4,40);
        this.outputText.setBackground(Color.WHITE);
        this.outputText.setBorder(BorderFactory.createTitledBorder("Description of the Document"));
        this.outputText.setFont(new Font("Serif", Font.BOLD, 24));
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {	
        try
        {
            if(event.getSource() == super.listOfDoc)//when button 1 is clicked
            {
                this.docName  = super.listOfDoc.getSelectedItem().toString();;
                SetOutput(this.docName,this.exclWords);//calling method SetOutput()
                System.out.println("document clicked");   
            }
            else if(event.getSource() == super.button1)//when button 1 is clicked
            {
                this.format = new CheckTextDocName();//instanciating Class CheckTextDocName
                this.docName  = this.format.CheckName(GetInput(super.TEXRFIELD1, this.docName));
                SetOutput(this.docName,this.exclWords);//calling method SetOutput()
                System.out.println("button1 clicked");   
            }
            else //when button 2 is clicked
            {
                if(!this.docName.isEmpty()) //name of document is given by user
                {
                    this.exclWords = GetInput(super.TEXTFIELD2, this.exclWords);
                    SetOutput(this.docName,this.exclWords);
                    System.out.println("button2 clicked"); 
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Please enter the name of the document first"); 
                } 
            }
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }  
    
    //Class method to get user's input form text field that takes in: JTextField and String. 
    //Assigning input to the String and returns the String
    public String GetInput(JTextField textField, String text)
    {
        textField.selectAll();
        text = textField.getText();
        JOptionPane.showMessageDialog(this,"<html><font size = 5>You enter: \""+ text+"\"! "
                                     +"Please press \"OK\" to continue or press \"x\""
                                     + " to exit</font></html>");
        textField.setText("");
        return text;
    }
    
    //Class method that gets required output and sets it to the textArea
    public void SetOutput(String input1, String input2)
    {
        try
        {
            //instanciating classes to read document and output description
            DocumentReader document = new DocumentReader();
            WordOccurencesCounter getWords    = new WordOccurencesCounter(document.ReadDocument(input1),input2.replaceAll(",", " "));
            this.docDescription     = getWords.DisplayTenMostUsed();
            this.outputText.setText("");
            this.outputText.append("The document - "+input1+" is about: "+ "\n\r");
            this.outputText.append(this.docDescription.substring(0,this.docDescription.length()-3));//to remove last comma
            add(this.outputText);

            setVisible(true); 
        }
        catch(Exception e)
        {
             System.out.println("Null Pointer ");
             this.outputText.setText("The system cannot find the document specified! Please Try again!");
             add(this.outputText);
             JOptionPane.showMessageDialog(this, "<html><font color=#CC0000; size = 6>"
                                          + "The system cannot find the document "
                                          + "specified! Please Try again!</font></html>");
        }
    }
}
