import os
import re
import subprocess

html_dir = r'..\testDownloadFile'
yt_dlp_path = r'..\tools\yt-dlp.exe'
downloaded_links = {}

for root, dirs, files in os.walk(html_dir):
    for file in files:
        if file.endswith(".html"):
            html_path = os.path.join(root, file)
            folder_name = file.replace(".html", "_files")
            target_folder = os.path.join(root, folder_name)
            os.makedirs(target_folder, exist_ok=True)

            with open(html_path, encoding='utf-8', errors='ignore') as f:
                content = f.read()

            # اگر لینک محلی mp3 یا mp4 داشت، اصلاً دانلود نکن
            has_local_media = re.search(r'src=["\'](?!https?://)[^"\']+\.(mp3|mp4)["\']', content, re.IGNORECASE)
            if has_local_media:
                print(f"Local media found in {file}, skipping download.")
                continue

            # پیدا کردن لینک‌های اینترنتی mp3/mp4
            matches = re.findall(r'(https?://[^"\']+\.(mp3|mp4))', content, re.IGNORECASE)

            found_mp3 = None
            found_mp4 = None
            for match in matches:
                url = match[0]
                ext = url.split('.')[-1].lower()
                if ext == 'mp3' and not found_mp3:
                    found_mp3 = url
                elif ext == 'mp4' and not found_mp4:
                    found_mp4 = url
                if found_mp3 and found_mp4:
                    break  # فقط یکی از هر نوع

            selected_links = []
            if found_mp3:
                selected_links.append(found_mp3)
            if found_mp4:
                selected_links.append(found_mp4)

            players_html = ""

            for link in selected_links:
                extension = link.split('.')[-1].lower()

                if link in downloaded_links:
                    output_name = downloaded_links[link]
                else:
                    output_name = f"media_{len(downloaded_links)}.{extension}"
                    output_path = os.path.join(target_folder, output_name)

                    print(f"Downloading {link} as {output_name}")
                    try:
                        subprocess.run([
                            yt_dlp_path,
                            '-o', output_path,
                            link
                        ], check=True)
                        downloaded_links[link] = output_name
                    except subprocess.CalledProcessError as e:
                        print(f"Error downloading {link}: {e}")
                        continue

                local_src = os.path.join(folder_name, output_name).replace('\\', '/')

                if extension == 'mp3':
                    players_html += f'''
                    <audio controls style="display:block; margin:10px auto; width:100%; max-width:600px; background-color:#f0f0f0; border-radius:8px;">
                        <source src="{local_src}" type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>\n'''
                elif extension == 'mp4':
                    players_html += f'''
                    <video controls style="display:block; margin:10px auto; width:100%; max-width:800px; background-color:#f0f0f0; border-radius:8px;">
                        <source src="{local_src}" type="video/mp4">
                        Your browser does not support the video element.
                    </video>\n'''

            # حذف لینک‌های غیر از mp3/mp4
            content = re.sub(r'<a\s+href=["\'](?!https?://[^"\']+\.(mp3|mp4))([^"\']+)["\'][^>]*>.*?</a>', '', content)

            # درج پلیر در جای مناسب
            if players_html:
                inserted = False
                for tag in [ 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'span','p']:
                    pattern = fr'(?i)(<{tag}[^>]*>)'
                    new_content, count = re.subn(pattern, players_html + r'\1', content, count=1)
                    if count > 0:
                        content = new_content
                        inserted = True
                        break
                if not inserted:
                    content = players_html + content

                with open(html_path, 'w', encoding='utf-8') as f:
                    f.write(content)
