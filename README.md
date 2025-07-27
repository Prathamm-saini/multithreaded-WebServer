Multi-threaded WebServer Performance Tester
A modern web-based performance testing tool for multi-threaded Java servers. Test your server's concurrent request handling capabilities with an intuitive, real-time interface.

<img width="1920" height="933" alt="Screenshot from 2025-07-27 16-26-38" src="https://github.com/user-attachments/assets/29bd5141-19b0-4f03-af82-c8cbf0ce0314" />

<img width="1920" height="933" alt="Screenshot from 2025-07-27 16-33-05" src="https://github.com/user-attachments/assets/d83f78a6-1d11-4350-a14c-79eec78f3df9" />

🚀 Features

Real-time Testing: Send concurrent HTTP requests to your Java server
Performance Metrics: View average response time, throughput, and success rates
Modern UI: Clean, dark-themed interface with glassmorphism design
Live Logging: Monitor all requests and responses in real-time
Responsive Design: Works perfectly on desktop and mobile devices
Simple Setup: No complex configuration required


📋 Prerequisites

A Java multi-threaded server running on port 9010
Server must have a /test endpoint that accepts GET requests
CORS enabled on your server for local testing


🛠️ Installation

Clone the repository
bashgit clone https://github.com/Prathamm-saini/multithreaded-WebServer.git
cd multithreaded-WebServer


🏃‍♂️ Quick Start

Start your Java server on port 9010 with /test endpoint
Open the tester in your web browser
Set concurrent requests (default: 10)
Click "Test Server" to start performance testing
View results and logs in real-time


🎯 Usage
Basic Testing

Enter number of concurrent requests (1-1000)
Click "Test Server"
Monitor real-time results and logs


Understanding Results

Average Response Time: Mean time for successful requests
Throughput: Requests processed per second
Success Rate: Percentage of successful requests
Real-time Logs: Individual request status and timing


Sidebar: Navigation and future features (single-threaded, thread pool servers)
Main Panel: Primary testing interface for multi-threaded server
Results Section: Performance metrics and status display
Logs Panel: Real-time request monitoring with color-coded status


⚡ Performance Testing
The tester sends concurrent HTTP requests to evaluate:

Concurrency Handling: How well your server handles multiple simultaneous requests
Response Time: Average time to process requests under load
Throughput: Requests per second your server can handle
Reliability: Success rate under concurrent load


🤝 Contributing

Fork the repository
Create a feature branch (git checkout -b feature/amazing-feature)
Commit your changes (git commit -m 'Add some amazing feature')
Push to the branch (git push origin feature/amazing-feature)
Open a Pull Request


📝 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Author
Pratham Saini

⭐ Star this repository if you found it helpful!




