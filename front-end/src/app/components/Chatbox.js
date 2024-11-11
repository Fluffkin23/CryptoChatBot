import {useState} from "react";
import PromptMessage from "@/app/components/PromptMessage";
import ResponseMessage from "@/app/components/ResponseMessage";
import axios from "axios";

const Chatbox = () => {

    const [responses,setResponses] = useState([]);
    const[loading, setLoading] = useState(false);

    const handlerFetchCoinDetails = async (prompt) => {
        setLoading(true);
        const userPrompt = { message: prompt, role: "user" };
        setResponses((prev) => [...prev, userPrompt]); // Add user message here

        try {
            const { data } = await axios.post("http://localhost:5454/ai/chat", { prompt });
            const response = { message: data.message, role: "model" };
            setResponses((prev) => [...prev, response]); // Add response here
            console.log("Success, ", data);
        } catch (error) {
            console.log("fetching.. ", prompt);
        }
        setLoading(false);
    };
    return(
        <div className="chat-box blur-background large-shadow z-50 bg-[#000518]
        bg-opacity-70 w-[90vw] md:w-[70vw] lg:w-[40vw] pb-6 h-[85vh]">
            <div className="h-[13%] pl-3 border-b border-gray-700 flex gap-x-4 items-center">
                <img className="rounded-full w-12 h-12"
                     src="https://images.unsplash.com/photo-1727976823180-314b097d2572?q=80&w=1168&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="" />
                <div>
                    <h1 className="text-lg font-semibold">AI CHAT BOT</h1>
                    <p className="text-sm text-gray-400"> Real time Crypto Market Data</p>
                </div>
            </div>
           <div className="h-[77%]">
               <div className="flex flex-col py-5 px-5 overflow-y-auto h-full custom-scrollbar">
                   {responses.map((item,index) =>
                   item.role === "user" ?
                   <div className="self-end" key = {index}>
                       <PromptMessage message={item.message}/>
                   </div> : <div className="self-start" key={index}> <ResponseMessage message={item.message}/> </div>
                   )}
               </div>
               <div className="p-10 gap-5 h-full flex flex-col justify-center items-center">
                   <p className="text-2xl font-bold"> Welcome to the crypto chat bot</p>
                   <p className="text-gray-500"> Inquire about market data</p>
               </div>
           </div>
            <div className="h-[10%] px-5">
                <input
                    onKeyPress={(e) => {
                        if (e.key === 'Enter') {
                            handlerFetchCoinDetails(e.target.value);
                            e.target.value = ""; // Clear the input field after submission
                        }
                    }
                }
                    // onChange={(e) => console.log(e.target.value)}
                    type="text" className="h-full rounded-full border-gray-700 border bg-transparent px-5 w-full
                outline-none" placeholder="Ask something..."/>
            </div>
        </div>
    )
}

export default Chatbox