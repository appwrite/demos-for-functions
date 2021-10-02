from time import strftime
from tkinter import Label, Tk

#======= Configuring window =========
#setting up interface
window = Tk()
window.title("")
window.geometry("200x80")
window.configure(bg="green")
window.resizable(False, False)

clock_label = Label(window, bg="green", fg="white", font = ("Times", 30, 'bold'), relief='flat')
clock_label.place(x = 20, y = 20)

def update_label():
    current_time = strftime('%H: %M: %S') #actucal time
    clock_label.configure(text = current_time)
    clock_label.after(80, update_label)

update_label()
window.mainloop()
