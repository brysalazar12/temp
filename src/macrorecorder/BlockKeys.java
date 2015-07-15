/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macrorecorder;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

/**
 *
 * @author Admin
 */
public class BlockKeys extends Thread {
private User32 lib;
private LowLevelKeyboardProc keyboardHook;
private static HHOOK hhk;

public void run() {
    lib = User32.INSTANCE;
    HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
    keyboardHook = new LowLevelKeyboardProc() {
        public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {

                if (nCode >= 0) {   
                    //To unhook press 'esc' key
                    if(info.vkCode == 0x1B) {
                        User32.INSTANCE.UnhookWindowsHookEx(hhk);
                    }
                    switch (info.vkCode){   
                        case 0x5B:System.err.println("l win"); return new LRESULT(1);
                        case 0x5C:System.err.println("r win"); return new LRESULT(1);
                        case 0xA2:System.err.println("l ctrl"); return new LRESULT(1);
                        case 0xA3:System.err.println("r ctrl"); return new LRESULT(1);
                        case 0xA4:System.err.println("l alt"); return new LRESULT(1);
                        case 0xA5:System.err.println("r alt"); return new LRESULT(1); 
                        default: System.out.println("Key Pressed : "+info.vkCode);//do nothing
                    }
                }  return lib.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
            }            

    };
    hhk = lib.SetWindowsHookEx(13, keyboardHook, hMod, 0);
    // This bit never returns from GetMessage
    int result;
    MSG msg = new MSG();

    while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
        if (result == -1) {
            break;
        } else {
            lib.TranslateMessage(msg);
            lib.DispatchMessage(msg);
        }
    }

    lib.UnhookWindowsHookEx(hhk);          

}

public static void main(String[] args) {
    Thread t = new Thread(new BlockKeys());
    t.start();
}
}
