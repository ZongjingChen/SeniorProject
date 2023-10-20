import os
import sys
import webbrowser
import time
import re
import threading


class Server(threading.Thread):
    def __init__(self):
        super().__init__()

    def run(self):
        try:
            os.system("python -m http.server 8080 --directory " + root_path)
        except Exception as e:
            print(e)
        finally:
            exit(1)


# use these lines if running source code
# template_path = '../templates/template.html'
# error_log_path = './Log/CompileError.log'
args = sys.argv
root_path = args[0].replace("windows_runner.exe", "")
root_path = args[0].replace("windows_runner.py", "")
print(root_path)

template_path = root_path + '.\\templates\\template.html'
error_log_path = root_path + '\\log\\CompileError.log'

# if open file by executable
if len(args) > 1:
    cr_code_path = args[1].split('\\')[-1]
else:
    cr_code_path = input("Please enter the file name: ")
    while not os.path.isfile(cr_code_path):
        print("File does not exist!")
        cr_code_path = input("Please enter the file name: ")

# get file name
file_name = cr_code_path.split('.')[0]
cr_code_path = root_path + cr_code_path

# use current timestamp to name the temp file
js_file_path = root_path + str(int(time.time()))
out_file_path = root_path + file_name + '.html'

with open(template_path, 'r') as f:
    content = f.read()

os.system(
    root_path + "jdk-13.0.2\\bin\\java.exe -jar " + root_path + "CRCompiler.jar " + cr_code_path + " " + js_file_path
    + " " + root_path + "Log\\CompileError.log")

try:
    with open(js_file_path, 'r') as f:
        code = f.read()
        # print(code)
        modelDecl = re.search(r'MODELDECL\n(.*)\nEND', code).group(1)
        statements = re.search(r'STATEMENTS\n((.*\n)*)END', code).group(1)
except AttributeError as e:
    with open(error_log_path, 'r') as f:
        # print(f.read())
        pass
    sys.exit(1)
finally:
    os.system("del " + js_file_path)

content = content.replace("MODEL_PATH_POSITION", modelDecl)
content = content.replace("OUTPUT_POSITION", statements)
# print(content)
with open(out_file_path, 'w') as f:
    f.write(content)

server = Server()
# server.daemon = True
server.start()

webbrowser.open_new_tab("http://localhost:8080/" + file_name + '.html')
# os.wait()

