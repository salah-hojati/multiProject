wsl -d Ubuntu-24.04

rem sudo tar -xvzf ideaIU-2024.3.3.tar.gz -C /opt

rem /opt/idea-IU-243.24978.46/bin/./idea.sh

rem sudo apt update
rem sudo apt install libreoffice

sudo apt update
sudo apt install -y gedit


********1. Update the package list:

sudo apt update

2. Download the latest Google Chrome .deb package:

wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb

3. Install Google Chrome:

sudo apt install ./google-chrome-stable_current_amd64.deb

4. Verify Installation:

Run the following command to check if Chrome is installed correctly:

google-chrome --version

5. Launch Google Chrome:

You can start Chrome using:

google-chrome

or from Activities > Search "Google Chrome".
******************

To install Python 3.12.5 on Ubuntu, follow these steps:
1. Update System Packages

sudo apt update && sudo apt upgrade -y

2. Install Required Dependencies

sudo apt install -y software-properties-common \
  build-essential libssl-dev zlib1g-dev \
  libncurses5-dev libnss3-dev libreadline-dev \
  libffi-dev curl libsqlite3-dev

3. Download and Compile Python 3.12.5

cd /usr/src
sudo curl -O https://www.python.org/ftp/python/3.12.5/Python-3.12.5.tgz

Extract the tarball:

sudo tar -xf Python-3.12.5.tgz
cd Python-3.12.5

4. Build and Install Python

sudo ./configure --enable-optimizations
sudo make -j$(nproc)
sudo make altinstall

    Note: We use make altinstall instead of make install to avoid overwriting python3 symlink in /usr/bin/.

5. Verify Installation

python3.12 --version

You should see:

Python 3.12.5

6. Set Python 3.12 as Default (Optional)

If you want python3 to point to Python 3.12:

sudo update-alternatives --install /usr/bin/python3 python3 /usr/local/bin/python3.12 1
sudo update-alternatives --config python3

Select Python 3.12 from the list.

Check the default version:

python3 --version

************************
python -m pip install -U aider-chat

# You may need to install google-generativeai
pip install -U google-generativeai

# Or with pipx...
pipx inject aider-chat google-generativeai

export GEMINI_API_KEY=<key> # Mac/Linux
setx   GEMINI_API_KEY <key> # Windows, restart shell after setx

aider --model gemini/gemini-1.5-pro-latest

# List models available from Gemini
aider --list-models gemini/
**************************************