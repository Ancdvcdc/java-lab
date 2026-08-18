package vn.edu.eaut.lab5.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Chặn nhập sai định dạng số điện thoại ngay trên GUI:
 * chỉ cho phép nhập chữ số, tối đa 10 ký tự.
 */
public class PhoneDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + string + currentText.substring(offset);
        if (newText.matches("\\d{0,10}")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {
        if (text == null) text = "";
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
        if (newText.matches("\\d{0,10}")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
