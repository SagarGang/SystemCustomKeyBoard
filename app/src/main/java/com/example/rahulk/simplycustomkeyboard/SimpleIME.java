package com.example.rahulk.simplycustomkeyboard;

import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import static com.example.rahulk.simplycustomkeyboard.AppConstants.DOUBLE_PRESS_INTERVAL;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.capsLetterEnter;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.isModeChanged;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.isModeChangedLetters;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.lastPressTime;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.mHasDoubleClicked;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.mHasFirstEmptyWithModeChange;
import static com.example.rahulk.simplycustomkeyboard.AppConstants.shiftOn;

public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard, mNonSymbolsKeyboard, mSymbolsShiftedKeyboard;
    int mKeyboardState = R.integer.keyboard_normal;
    int shiftDelCount = 0;
    private boolean caps = false;
    CharSequence currentText = "", beforeDelText = "";
    InputConnection inputConnection = null;

    @Override
    public View onCreateInputView() {

        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty_ver_capitalized, R.integer.keyboard_normal);
        try {
            if (screenOrentaion() == Configuration.ORIENTATION_PORTRAIT) {
                keyboard = new Keyboard(this, R.xml.qwerty_ver_capitalized, R.integer.keyboard_normal);

            } else {
                keyboard = new Keyboard(this, R.xml.qwerty_hor_capitalized, R.integer.keyboard_normal);
            }
            keyboardView.setKeyboard(keyboard);
            keyboardView.setOnKeyboardActionListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }

        keyboardView.setPreviewEnabled(true);

        showKeyBoard();
        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        inputConnection = getCurrentInputConnection();
        char code = (char) primaryCode;
//        playClick(primaryCode);
        beforeDelText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;
        switch (primaryCode) {

            case Keyboard.KEYCODE_DONE:
                final int options = this.getCurrentInputEditorInfo().imeOptions;
                final int actionId = options & EditorInfo.IME_MASK_ACTION;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        sendDefaultEditorAction(true);
                        break;

                    case EditorInfo.IME_ACTION_GO:
                        sendDefaultEditorAction(true);
                        break;


                    case EditorInfo.IME_ACTION_SEND:
                        sendDefaultEditorAction(true);
                        caps = true;
                        setKeboradViewOnChange(true, keyboardView);
                        break;

                    case 6:
                        sendDefaultEditorAction(true);
                        caps = true;
                        setKeboradViewOnChange(true, keyboardView);
                        break;

                    default:
                        sendDefaultEditorAction(true);
                        caps = true;
                        setKeboradViewOnChange(true, keyboardView);
                        break;

                }

                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;

            case Keyboard.KEYCODE_SHIFT:
                try {
                    if (!shiftOn) {
                        onDoubleClick();
                        break;
                    } else {
                        if (!caps)
                            caps = !caps;

                        lastPressTime = 0;
                        shiftOn = false;
                        keyboardShift();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:

                keyboard = keyboardView.getKeyboard();
                keyCodeModeChange(keyboard);

                break;

            case 12:

                mKeyboardState = R.integer.keyboard_symbol;
                keyboard = keyboardView.getKeyboard();
                keyCodeModeChange(keyboard);

                try {
                    InputMethodManager mgr =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (mgr != null) {
                        mgr.showInputMethodPicker();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;


            case Keyboard.KEYCODE_DELETE:
                try {
                    beforeDelText = inputConnection.getTextBeforeCursor(currentText.length(), 0);
                    inputConnection.deleteSurroundingText(1, 0);
                    currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

                    if (currentText.toString().length() != 0 && ".".equalsIgnoreCase(currentText.toString().substring(currentText.length() - 1))) {
                        caps = false;
                        setKeboradViewOnChange(false, keyboardView);
                    } else {

                   /* myLoop:*/
                        if (!TextUtils.isEmpty(beforeDelText)) {


                            onKeyDel();
//                        break myLoop;
                        }
                    }


                    if (caps) {
                        caps = false;
                        setKeboradViewOnChange(false, keyboardView);
                    }

                    if (currentText.toString().isEmpty()) {
                        caps = true;
                        setKeboradViewOnChange(true, keyboardView);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    break;
                }

            default:

                try {
                    if (Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    } else {
                        capsLetterEnter = false;
                    }
                    inputConnection.commitText(String.valueOf(code), 1);

                    currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

                    if (!shiftOn || !mHasDoubleClicked && !isModeChanged) {
                        shiftDelCount = 0;
                        setShiftedKeyoboardOnSpaceAndDot(currentText, true);
                    } else {
                        lastPressTime = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }

    }

    @Override
    public void onPress(int primaryCode) {
        try {
            Log.e("Simple ","onPress");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }

    @Override
    public void onRelease(int primaryCode) {
        try {
            Log.e("Simple ","onRelease");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onText(CharSequence text) {
        //do Something  ;
        Log.e("Simple ","onText");
    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeLeft() {
        //do Something  ;
    }


    @Override
    public void swipeRight() {
        //do Something  ;
    }

    @Override
    public void swipeUp() {
        //do Something  ;

    }


    public int screenOrentaion() {
        return this.getResources().getConfiguration().orientation;
    }


    @Override
    public View onCreateExtractTextView() {
        return super.onCreateExtractTextView();

    }

    public void keyboardShift() {
        try {
            caps = !caps;
            keyboard.setShifted(caps);
            keyboardView.invalidateAllKeys();
            setKeboradViewOnChange(caps, keyboardView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setShiftedKeyoboardOnSpaceAndDot(CharSequence currentText, boolean capsFlag) {

        try {
            myLoop:
            if (currentText.length() >= 2) {
                shiftDelCount = 0;
                String newString = currentText.toString().substring(currentText.length() - 2);
                if (newString.equalsIgnoreCase(AppConstants.spaceWithDot)) {
                    AppConstants.shiftOnSpace = true;
                    keyboardShift();
                } else {
                    newString = currentText.toString().substring(currentText.length() - 1);
                    if (capsFlag) {
                        caps = true;
                        keyboardShift();
                    } else {
                        if (newString.equalsIgnoreCase(AppConstants.Whitespace) && AppConstants.shiftOnSpace) {
                            AppConstants.shiftOnSpace = false;
                            keyboardShift();
                        }
                    }

                }
                Log.v("SelectedData", newString);
                break myLoop;
            } else if (capsFlag) {


                if ((currentText.length() == 1) && !currentText.equals(currentText.toString().toLowerCase()) && !(currentText.toString().matches("^\\s*$"))) {


                    isModeChangedLetters = false;
                    if (shiftDelCount < 1) {
                        keyboardShift();
                        shiftDelCount++;
                    }
                }

                break myLoop;

            } else if (TextUtils.isEmpty(currentText)) {
                if (shiftDelCount < 1) {
                    keyboardShift();
                    shiftDelCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        super.onStartInputView(info, restarting);


        caps = true;
        setKeboradViewOnChange(true, keyboardView);

        if (mKeyboardState != R.integer.keyboard_normal) {
            isModeChanged = false;
            keyboard = keyboardView.getKeyboard();

            keyCodeModeChange(keyboard);
        }

    }

    public void onDoubleClick() {
        try {
            long iCurrentTime = 0;
            iCurrentTime = System.currentTimeMillis();
            if ((iCurrentTime - lastPressTime) <= (DOUBLE_PRESS_INTERVAL)) {
                if (caps) {
                    caps = !caps;
                }
                shiftOn = true;
                mHasDoubleClicked = true;

                lastPressTime = 0;
                keyboardShift();
            } else {

                shiftOn = false;
                keyboardShift();
                mHasDoubleClicked = false;
            }
            lastPressTime = iCurrentTime;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }


    public void setKeboradViewOnChange(boolean flagCaps, KeyboardView kView) {
        try {

            if (!isModeChanged) {
                if (screenOrentaion() == Configuration.ORIENTATION_PORTRAIT) {
                    if (flagCaps) {
                        keyboard = new Keyboard(this, R.xml.qwerty_ver_capitalized, R.integer.keyboard_normal);
                    } else {
                        keyboard = new Keyboard(this, R.xml.qwerty_vertical, R.integer.keyboard_normal);
                    }
                } else {
                    if (flagCaps) {

                        keyboard = new Keyboard(this, R.xml.qwerty_hor_capitalized, R.integer.keyboard_normal);
                    } else {
                        keyboard = new Keyboard(this, R.xml.qwerty_horizontal, R.integer.keyboard_normal);
                    }

                }

            } else {
                if (screenOrentaion() == Configuration.ORIENTATION_PORTRAIT) {
                    keyboard = new Keyboard(this, R.xml.qwerty_vertical, R.integer.keyboard_symbol);

                } else {
                    keyboard = new Keyboard(this, R.xml.qwerty_horizontal, R.integer.keyboard_symbol);
                }
            }
            kView.setKeyboard(keyboard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onKeyDel() {

        try {
           /* myLoop:*/
            if (beforeDelText.length() >= 1) {


                currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;
                if (!shiftOn) {
                    setShiftedKeyoboardOnSpaceAndDot(currentText, false);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyCodeModeChange(Keyboard keyboard) {
        try {
            if (keyboard != null) {
                if (mKeyboardState == R.integer.keyboard_normal) {
                    isModeChanged = true;
                    mHasFirstEmptyWithModeChange = true;
                    if (mSymbolsShiftedKeyboard == null) {
                        setKeboradViewOnChange(caps, keyboardView);
                    }
                    mKeyboardState = R.integer.keyboard_symbol;

                } else {
                    if (mNonSymbolsKeyboard == null) {
                        isModeChanged = false;
                        setKeboradViewOnChange(caps, keyboardView);
                    }
                    mKeyboardState = R.integer.keyboard_normal;

                    keyboardView.setShifted(false);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}