import os
import sys
import webbrowser
import time
import re
import threading

template_path = '../templates/template.html'
error_log_path = './Log/CompileError.log'

cr_code_path = input("Please enter the file name: ")
while not os.path.isfile(cr_code_path):
    print("File does not exist!")
    cr_code_path = input("Please enter the file name: ")

file_name = cr_code_path.split('.')[0]
js_file_path = str(int(time.time()))
out_file_path = file_name + '.html'

with open(template_path, 'r') as f:
    content = f.read()

os.system("..\\CRCompiler\\out\\artifacts\\CRCompiler_jar\\CRCompiler.jar " + cr_code_path + " " + js_file_path)

try:
    with open(js_file_path, 'r') as f:
        code = f.read()
        # print(code)
        modelDecl = re.search(r'MODELDECL\n(.*)\nEND', code).group(1)
        statements = re.search(r'STATEMENTS\n((.*\n)*)END', code).group(1)
except AttributeError as e:
    with open(error_log_path, 'r') as f:
        print(f.read())
    sys.exit(1)
finally:
    os.system("del " + js_file_path)

content = content.replace("MODEL_PATH_POSITION", modelDecl)
content = content.replace("OUTPUT_POSITION", statements)
# print(content)
with open(out_file_path, 'w') as f:
    f.write(content)

class Server(threading.Thread):
    def __init__(self):
        super().__init__()
    def run(self):
        os.system("python -m http.server 8080 --directory ../")

server = Server()
# server.daemon = True
server.start()

webbrowser.open_new_tab("http://localhost:8080/script/" + out_file_path)
# os.wait()


# webbrowser.open_new_tab("http://localhost:63342/SeniorProject/script/" + out_file_path)





