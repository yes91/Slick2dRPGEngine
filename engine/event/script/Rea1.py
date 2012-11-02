from java.lang import Boolean
import time

evcom.allowClose(Boolean.FALSE)
evcom.showText("This is a .py script.")
time.sleep(2)
evcom.showText("This has been a timing example")
time.sleep(1)
evcom.allowClose(Boolean.TRUE)
