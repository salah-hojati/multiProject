python extract_links.py


🎯 Purpose of the Script:
This script:

Scans a folder for .html files.

Extracts all media links (.mp3, .mp4, .webm, .m3u8) from those HTML files.

Downloads those media files using yt-dlp.

Saves them into a subfolder per HTML file.

Replaces the original online URLs in the HTML with local file paths and appropriate <audio> or <video> tags.

🔍 Step-by-Step Breakdown:
1. Set directories and initialize variables:
   python
   Copy
   Edit
   html_dir = r'D:\testDownloadFile'
   yt_dlp_path = r'D:\مسیر\به\yt-dlp.exe'
   downloaded_links = {}
   html_dir: Folder containing .html files.

yt_dlp_path: Path to the yt-dlp executable.

downloaded_links: Keeps track of already-downloaded links to avoid duplicates.

2. Loop through .html files:
   python
   Copy
   Edit
   for file in os.listdir(html_dir):
   if file.endswith(".html"):
   Only processes files ending in .html.

3. Create a folder for each HTML file:
   python
   Copy
   Edit
   folder_name = file.replace(".html", "_files")
   This subfolder stores downloaded media related to that HTML file.

4. Read the HTML and extract media links:
   python
   Copy
   Edit
   matches = re.findall(r'(https?://[^"\']+\.(mp3|mp4|webm|m3u8))', content, re.IGNORECASE)
   Uses a regex to find all URLs that end with the specified media formats.

5. Download each media file using yt-dlp:
   python
   Copy
   Edit
   subprocess.run([yt_dlp_path, '-o', output_path, link], check=True)
   Saves each media file locally using yt-dlp.

If already downloaded, skips it.

6. Replace the original link in HTML:
   python
   Copy
   Edit
   replacement_tag = f'<video>...</video>'  # or <audio> or <a> depending on file type
   content = re.sub(pattern, replacement_tag, content)
   Generates the correct HTML tag (<video>, <audio>, or a simple <a> link).

Replaces all instances of the original link with the local version.

7. Write the updated HTML back to disk:
   python
   Copy
   Edit
   with open(html_path, 'w', encoding='utf-8') as f:
   f.write(content)
   Saves the modified HTML with offline references.

✅ End Result:
You’ll end up with:

A folder containing your original HTML files.

For each HTML file:

A subfolder with all media content downloaded.

The HTML file updated to use local files instead of remote URLs.

<audio> and <video> players embedded properly.

This makes your HTML pages fully offline-capable.

