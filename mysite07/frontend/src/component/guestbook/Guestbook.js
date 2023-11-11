import React, {useRef, useState, useEffect} from 'react';
import {MySiteLayout} from "../../layout";
import styles from '../../assets/scss/component/guestbook/Guestbook.scss';
import form_styles from '../../assets/scss/component/guestbook/WriteForm.scss';

function Guestbook(props) {
    const refForm = useRef(null);
    const [guestbooks, setGuestbook] = useState(null);

    const addGuestbook = async (guestbook) => {
        try{
            const response = await fetch('/api/guestbook',{
                method:'post',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept':'application/json'
                },
                body: JSON.stringify(guestbook)
            });
    
            if(!response.ok){
                throw new Error(`${response.status} ${response.statusText}`);
            }
    
            const json = await response.json();

            if(json.result !== 'success'){
                throw new Error(`${json.result} ${json.message}`);
            }
            
            console.log(json.data);
            // const newEmails = [json.data, ...guestbooks];
            // setEmails(newEmails);

        }catch(e){
            console.error(e);
        }
        

    }

    const fetchGuestbooks = async () => {
        try{
            const response = await fetch(`api/guestbook`, {
                method: 'get',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept':'application/json'
                },
                body: null
    
            });
    
            if(!response.ok){
                throw new Error(`${response.status} ${response.statusText}`);
            }

            const json = await response.json();
            if(json.result !== 'success'){
                throw new Error(`${json.result} ${json.message}`);
            }

            
            setGuestbook(json.data);
        
        }catch(e){
            console.error(e);
        }
    }
    
    

    useEffect(() =>{
        fetchGuestbooks();
        
    }, []);


    return (
        <MySiteLayout>
            <div className={styles.Guestbook}>
                <h2>방명록</h2>
                <form 
                    // 폼을 리셋하는 기능
                    ref={refForm}
                    className={form_styles.WriteForm}
                    onSubmit={(e)=>{
                        e.preventDefault();

                        const guestbook =  {
                            no: null,
                            name: e.target.inputName.value,
                            password: e.target.inputPassword.value,
                            contents : e.target.txContent.value
                        };
                        console.log(guestbook);
                        addGuestbook(guestbook);
                        
                        refForm.current.reset();
                    }}
                        >
                    <input type='text' name='inputName' placeholder='이름' className={form_styles.InputName} />
                    <input type='password' name='inputPassword' placeholder='비밀번호' className={form_styles.inputPassword} />
                    <textarea id="tx-content" name='txContent' placeholder="내용을 입력해 주세요." className={form_styles.txContent}/>
                    <input type='submit' value='보내기' />
                </form>
                {/* {guestbooks == null?
                    []:
                    <Guestbooklist guestbooks={guestbooks}/>
                    } */}
            </div>
        </MySiteLayout>
    );
}

export default Guestbook;