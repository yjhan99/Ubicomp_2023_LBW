if [ $# -eq 0 ]; then
  adb shell dumpsys deviceidle unforce
  adb shell dumpsys deviceidle disable
  adb shell dumpsys deviceidle whitelist +io.github.qobiljon.stress
else
  adb -s "$1" shell dumpsys deviceidle unforce
  adb -s "$1" shell dumpsys deviceidle disable
  adb -s "$1" shell dumpsys deviceidle whitelist +io.github.qobiljon.stress
fi
