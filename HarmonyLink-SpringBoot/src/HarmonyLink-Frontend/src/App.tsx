import {useEffect, useState} from "react";
import axios from "axios";
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [hello, setHello] = useState('');

  useEffect(() => {
      axios.get('/api/test')
          .then((res) => {
              setHello(res.data);
          })
  }, []);
  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <p>백엔드 데이터: { hello }</p>
      </div>
    </>
  )
}

export default App
