// Simple multithreaded server tester
function addLog(message, type = 'info') {
    const logContent = document.getElementById('logContent');
    const logEntry = document.createElement('div');
    logEntry.className = `log-entry ${type}`;
    logEntry.textContent = `[${new Date().toLocaleTimeString()}] ${message}`;
    logContent.appendChild(logEntry);
    logContent.scrollTop = logContent.scrollHeight;
}

function showResult(message, isSuccess) {
    const resultDiv = document.getElementById('result-multi');
    resultDiv.textContent = message;
    resultDiv.className = `result ${isSuccess ? 'success' : 'error'}`;
}

// Test multi-threaded server
async function testServer(serverId, socketPort) {
    const clientCount = parseInt(document.getElementById('clientCount').value) || 10;
    const button = document.getElementById('testButton');
    const httpPort = 9010; // Fixed HTTP port

    button.disabled = true;
    button.textContent = 'Testing...';

    addLog(`Testing with ${clientCount} requests`, 'info');

    try {
        const startTime = Date.now();
        let successCount = 0;
        let totalTime = 0;

        // Make all requests
        const promises = [];
        for (let i = 0; i < clientCount; i++) {
            promises.push(makeRequest(httpPort));
        }

        const results = await Promise.all(promises.map(p => p.catch(e => ({ error: e.message }))));

        // Count successful requests
        results.forEach(result => {
            if (!result.error) {
                successCount++;
                totalTime += result.responseTime;
            }
        });

        const endTime = Date.now();
        const totalDuration = endTime - startTime;

        if (successCount > 0) {
            const avgTime = Math.round(totalTime / successCount);
            const throughput = (successCount * 1000 / totalDuration).toFixed(2);

            showResult(`✓ ${avgTime}ms avg | ${throughput} req/s | ${successCount}/${clientCount} success`, true);
            addLog(`Test completed: ${successCount} successful requests`, 'success');
        } else {
            showResult('✗ All requests failed', false);
            addLog('All requests failed - Check if server is running', 'error');
        }

    } catch (error) {
        showResult('✗ Server not running', false);
        addLog('Server not running on port ' + httpPort, 'error');
    }

    button.disabled = false;
    button.textContent = 'Test Server';
}

// Make single HTTP request
async function makeRequest(port) {
    const startTime = Date.now();

    try {
        const response = await fetch(`http://localhost:${port}/test`, {
            method: 'GET',
            mode: 'cors'
        });

        if (response.ok) {
            const text = await response.text();
            const responseTime = Date.now() - startTime;
            return { responseTime, data: text };
        } else {
            throw new Error(`HTTP ${response.status}`);
        }
    } catch (error) {
        throw new Error(`Connection failed: ${error.message}`);
    }
}

function clearLogs() {
    document.getElementById('logContent').innerHTML = '';
    addLog('Logs cleared', 'info');
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    addLog('Ready to test multi-threaded server on port 9010', 'info');
});