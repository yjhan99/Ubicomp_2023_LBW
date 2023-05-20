# Stress data collecton app for WearOS

## 1. Disabling doze mode

* Disabled Doze mode and whitelisted the application
    * Unforce doze mode – ```adb shell dumpsys deviceidle unforce```
    * Disable doze mode – ```adb shell dumpsys deviceidle disable```
    * Whitelist the application – ```adb shell dumpsys deviceidle whitelist +io.github.qobiljon.stress```

## 2. Disabling interruptions and extending battery life

| Setting                            | State                     |
|------------------------------------|---------------------------|
| OS: automatic workout detections   | OFF (to preserve battery) |
| OS: automatic heart rate checks    | OFF (to preserve battery) |
| OS: blood oxygen level checks      | OFF (to preserve battery) |
| OS: automatic inactive time checks | OFF (to preserve battery) |
| GPS sensor                         | OFF (to preserve battery) |
| Screen brightness                  | As low as possible        |
