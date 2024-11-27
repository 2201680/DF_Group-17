from flask import Flask, render_template, request


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

if __name__ == "__main__":
    app.run(debug=True)