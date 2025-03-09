import React, { useState, useRef, useEffect } from "react";
import axios from "axios";
import "./Chat.css";

const Chat = () => {
  const [question, setQuestion] = useState("");
  const [chatHistory, setChatHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const chatBodyRef = useRef(null);

  const handleAsk = async () => {
    if (!question.trim()) return;
    const userMessage = { sender: "user", message: question };
    setChatHistory((prev) => [...prev, userMessage]);
    setQuestion("");
    setLoading(true);

    try {
      const res = await axios.get(`http://localhost:8080/api/ask`, {
        params: { question },
      });
      const aiMessage = { sender: "ai", message: res.data };
      setChatHistory((prev) => [...prev, aiMessage]);
    } catch (error) {
      const errorMsg = { sender: "ai", message: "Error fetching response." };
      setChatHistory((prev) => [...prev, errorMsg]);
    }
    setLoading(false);
  };

  useEffect(() => {
    if (chatBodyRef.current) {
      chatBodyRef.current.scrollTop = chatBodyRef.current.scrollHeight;
    }
  }, [chatHistory]);

  return (
    <div className="chat-page">
      <div className="chat-container">
        <header className="chat-header">
          <h1>HTML AI</h1>
        </header>
        <main className="chat-body" ref={chatBodyRef}>
          {chatHistory.length === 0 && (
            <div className="chat-placeholder">
              Start the conversation...
            </div>
          )}
          {chatHistory.map((chat, index) => (
            <div
              key={index}
              className={`chat-bubble ${chat.sender === "user" ? "user-bubble" : "ai-bubble"}`}
            >
              {chat.message}
            </div>
          ))}
          {loading && <div className="chat-loading">Thinking…</div>}
        </main>
        <footer className="chat-footer">
          <input
            type="text"
            placeholder="Send a message..."
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleAsk()}
          />
          <button onClick={handleAsk}>Send</button>
        </footer>
      </div>
    </div>
  );
};

export default Chat;
