from flask import Flask, render_template, request, redirect, url_for


app = Flask(__name__)

@app.route('/hello')
def hello():
    print("Hello")
    return render_template("index.html")

@app.route('/updateFiles', methods=['POST'])
def update_files():
    text = request.form['inputText']
    with open('data.txt', 'a') as file:
        file.write(text + '\n')
    return 'Text appended to file successfully!'

@app.route('/clearFile', methods=['GET', 'POST'])
def clear_files():
    with open('data.txt', 'w') as file:
        file.truncate(0)
    return 'File cleared successfully!'

@app.route('/data',methods=['GET'])
def read_file():
    with open('data.txt', 'r') as file:
        text = file.readlines()
    return render_template('set_to_delete.html', data = text)

@app.route('/removeLine', methods=['POST'])
def remove_line():
    line_to_remove = request.form['line']
    print(line_to_remove)
    
    with open('data.txt', 'r') as file:
        lines = file.readlines()
    
    with open('data.txt', 'w') as file:
        for line in lines:
            if line.strip() != line_to_remove.strip():
                file.write(line)
    
    return redirect(url_for('read_file'))

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0')


